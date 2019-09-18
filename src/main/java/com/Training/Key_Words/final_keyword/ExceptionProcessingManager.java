package com.fisglobal.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.fisglobal.base.Application;
import com.fisglobal.base.ApplicationResources;
import com.fisglobal.base.Logger;
import com.fisglobal.base.SanchezException;
import com.fisglobal.bean.AccountExceptionDetails;
import com.fisglobal.bean.Agent;
import com.fisglobal.bean.CustomerExceptionDetails;
import com.fisglobal.bean.ExceptionDetail;
import com.fisglobal.bean.ExceptionProcessingService;
import com.fisglobal.bean.Restriction;
import com.fisglobal.bean.Stop;
import com.fisglobal.host.ProfileSQL;
import com.fisglobal.utils.Utility;

public final class ExceptionProcessingManager extends BaseManager {

	// STBLEXCTYP
	private static Map<String, String> exceptionTypes;
	// UTBLRETIRCD
	private static Map<String, String> exceptionPayReturnReasons;
	// STBLRETFACT
	private static Map<String, String> payReturnFeeActions;

	private static ExceptionProcessingManager exceptionProcessingManager;

	static {
		exceptionProcessingManager = new ExceptionProcessingManager();
	}

	private ExceptionProcessingManager() {
		reload();
	}

	public static void reload() {
		exceptionTypes = fillMap("STBLEXCTYP", "EXCTYP", "FMDESC", true);
		exceptionPayReturnReasons = fillMap("UTBLRETIRCD", "RETIRCD", "FMDESC", true);
		payReturnFeeActions = fillMap("STBLRETFACT", "ACTION", "FMDESC", true);

	}

	public static void initSearchCriteria(final Agent agent, final ExceptionProcessingService service, final SimpleDateFormat dateFormat, final SimpleDateFormat timeFormat) {

		String date = service.getDefaultDate();
		String source = service.getDefaultSource();
		String branch = service.getDefaultBranch();
		String status = service.getDefaultStatus();

		service.setTransactionDates(getTransactionDates(agent, dateFormat));

		// When page loads for the first time. Date is defaulted to the first item.
		if (!Utility.hasText(date)) {
			final Iterator<String> dateArr = service.getTransactionDates().keySet().iterator();
			if (service.getTransactionDates().size() > 0 && dateArr != null) {
				date = dateArr.next();
				service.setDefaultDate(date);
			}
		}

		if (Utility.hasText(date)) {
			service.setTransactionSources(getTransactionSources(agent, date, dateFormat, timeFormat));

			// When page loads for the first time. Source is defaulted to the first item.
			if (!Utility.hasText(source)) {
				final Iterator<String> sourceArr = service.getTransactionSources().keySet().iterator();
				if (service.getTransactionSources().size() > 0 && sourceArr != null) {
					source = sourceArr.next();
				}
			}

			if (Utility.hasText(source)) {
				service.setDefaultSource(source);
				service.setBranches(getBranches(agent, date, source, dateFormat));

				// When page loads for the first time. Branch is defaulted to the first item.
				if (!Utility.hasText(branch)) {
					final Iterator<String> branchArr = service.getBranches().keySet().iterator();
					if (service.getBranches().size() > 0 && branchArr != null) {
						branch = branchArr.next();
					}
				}
				if (Utility.hasText(branch)) {
					service.setDefaultBranch(branch);
				}
				service.setStatusOptions(getStatusOptions(agent, date, source, branch, dateFormat));
				if (!Utility.hasText(status) && !service.getStatusOptions().isEmpty()) {
					status = service.getStatusOptions().get(0);
					service.setDefaultStatus(status);
				}
				if (Utility.hasText(status)) {
					service.setDefaultStatus(status);
				}

				service.setCustomersMap(getCustomerNames(agent, date, source, branch, dateFormat));

			}
		}
	}

	private static LinkedHashMap<String, String> getTransactionDates(final Agent agent, final SimpleDateFormat dateFormat) {
		final LinkedHashMap<String, String> trnDates = new LinkedHashMap<String, String>();
		final String stmtStr1 = "SELECT DISTINCT TJD FROM RET ORDER BY TJD DESC";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, stmtStr1);
			rs = profile.executeQuery();
			while (rs.next()) {
				final Date trnDate = rs.getDate("TJD");
				final String trnDateString = dateFormat.format(trnDate);
				trnDates.put(trnDateString, trnDateString);
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getTransactionDates", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getTransactionDates", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}

		return trnDates;

	}

	private static LinkedHashMap<String, String> getBranches(final Agent agent, final String date, final String source, final SimpleDateFormat dateFormat) {
		final LinkedHashMap<String, String> branchDescriptions = new LinkedHashMap<String, String>();
		final String stmtStr1 = "SELECT DISTINCT RET.BRCD FROM RET WHERE RET.TJD = ? AND RET.SRC = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;

		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, Utility.toProfileDateString(dateFormat.parse(date)));
			profile.setString(2, source);
			rs = profile.executeQuery();
			while (rs.next()) {
				final String branchCode = rs.getString("RET.BRCD");
				final String branchCodeDescription = GlobalManager.getBranchCodeDescription(branchCode);

				if (branchCodeDescription == null) {
					branchDescriptions.put(branchCode, branchCode);
				} else {
					branchDescriptions.put(branchCode, branchCode.concat(" - ").concat(branchCodeDescription));
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getBranches", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getBranches", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return branchDescriptions;
	}

	private static Map<String, String> getTransactionSources(final Agent agent, final String date, final SimpleDateFormat dateFormat, final SimpleDateFormat timeFormat) {
		final LinkedHashMap<String, String> trnSourceList = new LinkedHashMap<String, String>();
		final String stmtStr1 = "SELECT DISTINCT SRC FROM RET WHERE TJD = ? ORDER BY SRC";
		ProfileSQL profile = null;
		ResultSet rs = null;

		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, Utility.toProfileDateString(dateFormat.parse(date)));
			rs = profile.executeQuery();
			while (rs.next()) {
				final String transactionSource = rs.getString("SRC");
				final String cutoffTime = getCutOffTimeForSource(agent, transactionSource, timeFormat);
				final String transactionDescription = GlobalManager.getInclearingDescriptionByKey(transactionSource);
				final StringBuilder sourceBuilder = new StringBuilder(transactionDescription);
				if (Utility.hasText(cutoffTime)) {
					sourceBuilder.append(" [").append(cutoffTime).append("]");
				}
				trnSourceList.put(transactionSource, sourceBuilder.toString());
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getTransactionSources", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getTransactionSources", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return trnSourceList;
	}

	private static LinkedHashMap<String, String> getCustomerNames(final Agent agent, final String date, final String source, final String branch, final SimpleDateFormat dateFormat) {
		final LinkedHashMap<String, String> trnCustomerList = new LinkedHashMap<String, String>();
		final String stmtStr1 = "SELECT DISTINCT RET.CID FROM RET WHERE RET.TJD = ? AND RET.SRC = ? AND RET.BRCD = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		final List<String> cidList = new ArrayList<String>();
		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, Utility.toProfileDateString(dateFormat.parse(date)));
			profile.setString(2, source);
			profile.setString(3, branch);
			rs = profile.executeQuery();
			while (rs.next()) {
				cidList.add(rs.getString("RET.CID"));
			}
			if (profile != null) {
				profile.close(rs);
			}

			if (cidList.size() > 0) {
				StringBuilder stmtStr2 = new StringBuilder("SELECT DISTINCT CIF.NAM, CIF.ACN FROM CIF, ACN WHERE CIF.ACN = ACN.ACN AND ACN.CID IN (");
				int j = 0;
				for (int i = 0; i < cidList.size(); ++i) {
					if (j > 0) {
						stmtStr2.append(",");
					}
					stmtStr2.append(cidList.get(i));
					j++;
					if (i > 0 && i % 100 == 0) {
						stmtStr2.append(")");
						profile = new ProfileSQL(agent, stmtStr2.toString());
						rs = profile.executeQuery();
						while (rs.next()) {
							final String trnCustomerId = rs.getString("CIF.ACN");
							final String trnCustomerName = rs.getString("CIF.NAM");
							trnCustomerList.put(trnCustomerId, trnCustomerName);
						}
						stmtStr2 = new StringBuilder("SELECT DISTINCT CIF.NAM, CIF.ACN FROM CIF, ACN WHERE CIF.ACN = ACN.ACN AND ACN.CID IN (");
						j = 0;
					}
				}
				stmtStr2.append(")");
				profile = new ProfileSQL(agent, stmtStr2.toString());
				rs = profile.executeQuery();
				while (rs.next()) {
					final String trnCustomerId = rs.getString("CIF.ACN");
					final String trnCustomerName = rs.getString("CIF.NAM");
					trnCustomerList.put(trnCustomerId, trnCustomerName);
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getCustomerNames", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getCustomerNames", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return trnCustomerList;
	}

	private static List<String> getStatusOptions(final Agent agent, final String date, final String source, final String branch, final SimpleDateFormat dateFormat) {
		final List<String> statusList = new ArrayList<String>();
		final String stmtStr1 = "SELECT DISTINCT RET.REVIEW FROM RET WHERE RET.TJD = ? AND RET.SRC = ? AND RET.BRCD = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;

		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, Utility.toProfileDateString(dateFormat.parse(date)));
			profile.setString(2, source);
			profile.setString(3, branch);
			rs = profile.executeQuery();
			while (rs.next()) {
				final boolean status = rs.getBoolean("RET.REVIEW");
				if (status) {
					statusList.add("1");
				} else {
					statusList.add("0");
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getStatusOptions", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getStatusOptions", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}

		return statusList;
	}

	private static String getCutOffTimeForSource(final Agent agent, final String source, final SimpleDateFormat timeFormat) {

		final Map<String, Time> userClassCutoffTime = new HashMap<String, Time>();
		Time inclearingCutoffTime = null;
		Time userClassReturnTime = null;
		String returnTime = "";

		// Retrieve CUTOFF time from CTBLINC
		inclearingCutoffTime = GlobalManager.getInclearingCutoffTimeByKey(source);

		if (userClassCutoffTime.get(agent.getUserClass()) == null) {
			final String query = "SELECT RETTIME FROM SCAU0 WHERE UCLS = ?";
			ProfileSQL profile = null;
			ResultSet rs = null;
			try {
				profile = new ProfileSQL(agent, query);
				profile.setString(1, agent.getUserClass());
				rs = profile.executeQuery();
				while (rs.next()) {
					if (rs.getString("RETTIME") != null) {
						userClassReturnTime = rs.getTime("RETTIME");
						userClassCutoffTime.put(agent.getUserClass(), userClassReturnTime);
					}
				}
			} catch (final SQLException e) {
				Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getCutoffTimeForSource", e.getMessage()});
				throw ProfileSQL.toSanchezException(e);
			} catch (final Exception e) {
				Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getCutoffTimeForSource", e.getMessage()});
			} finally {
				if (profile != null) {
					profile.close(rs);
				}
			}
		} else {
			userClassReturnTime = userClassCutoffTime.get(agent.getUserClass());
		}

		final String userClassCutoffTimeStr = userClassReturnTime == null ? null : timeFormat.format(userClassReturnTime);
		final String inclearingCutoffTimeStr = inclearingCutoffTime == null ? null : timeFormat.format(inclearingCutoffTime);
		// Determine which time is earlier.
		if (userClassReturnTime != null && inclearingCutoffTime != null) {
			if (userClassReturnTime.compareTo(inclearingCutoffTime) <= 0) {
				returnTime = userClassCutoffTimeStr;
			} else {
				returnTime = inclearingCutoffTimeStr;
			}
		} else if (userClassReturnTime == null && inclearingCutoffTime != null) {
			returnTime = inclearingCutoffTimeStr;
		} else if (inclearingCutoffTime == null && userClassReturnTime != null) {
			returnTime = userClassCutoffTimeStr;
		}

		return returnTime;
	}

	public static void fetchExceptionDetails(final Agent agent, final ExceptionProcessingService service, final SimpleDateFormat dateFormat) {

		final String transactionDate = service.getDefaultDate();
		final String source = service.getDefaultSource();
		final String branch = service.getDefaultBranch();
		String status = service.getDefaultStatus();
		if ("all".equals(status)) {
			status = "";
		}
		final String customerNumber = service.getCustomerNumber();

		ProfileSQL profile = null;
		ResultSet rs = null;
		CustomerExceptionDetails customerExceptionDetails;
		AccountExceptionDetails accountDetail = null;

		try {
			ExceptionDetail exceptionDetail;
			int totalComplete = 0;
			int totalInComplete = 0;
			final Map<String, CustomerExceptionDetails> searchResultList = new LinkedHashMap<String, CustomerExceptionDetails>();
			final List<String> additionalConstraints = new ArrayList<String>();
			final StringBuilder query = new StringBuilder("SELECT RET.CID, EXC.TOT, EXC.CK, EXC.EXCTYP, RET.PRETRES, RET.FEE, RET.NOTE, CIF.NAM, CIF.ACN, RET.TJD, RET.BRCD, RET.UID, RET.TSEQ, RET.RET, RET.REVIEW, EXC.TCMT FROM RET, EXC, ACN, CIF WHERE RET.TJD = EXC.TJD AND RET.BRCD = EXC.BRCD AND RET.UID = EXC.UID AND RET.TSEQ = EXC.TSEQ AND ACN.CID = RET.CID AND CIF.ACN = ACN.ACN AND RET.TJD = ? AND RET.SRC = ?");

			if (Utility.hasText(customerNumber)) {
				query.append(" AND CIF.ACN = ?");
				additionalConstraints.add(customerNumber);
			}

			if (Utility.hasText(branch)) {
				query.append(" AND RET.BRCD = ?");
				additionalConstraints.add(branch);
			}

			if (Utility.hasText(status)) {
				query.append(" AND RET.REVIEW = ?");
				additionalConstraints.add(status);
			}

			query.append(" ORDER BY CIF.NAM, RET.CID");

			profile = new ProfileSQL(agent, query.toString());
			profile.setString(1, Utility.toProfileDateString(dateFormat.parse(transactionDate)));
			profile.setString(2, source);

			for (int offset = 0; offset < additionalConstraints.size(); ++offset) {
				profile.setString(offset + 3, additionalConstraints.get(offset));
			}

			rs = profile.executeQuery();
			while (rs.next()) {
				exceptionDetail = new ExceptionDetail();
				final String accountNum = rs.getString("RET.CID");
				exceptionDetail.setAccountNumber(accountNum);
				final String customerNum = rs.getString("CIF.ACN");
				exceptionDetail.setAmount(rs.getString("EXC.TOT"));
				exceptionDetail.setCheckNumber(rs.getString("EXC.CK"));
				exceptionDetail.setExceptionType(getExceptionTypeDescription(rs.getString("EXC.EXCTYP")));
				final boolean reviewComplete = rs.getBoolean("RET.REVIEW");
				if (reviewComplete) {
					totalComplete++;
				} else {
					totalInComplete++;
				}
				exceptionDetail.setReviewComplete(reviewComplete);
				exceptionDetail.setPayReason(rs.getString("RET.PRETRES"));
				exceptionDetail.setPayFeeAction(rs.getString("RET.FEE"));
				exceptionDetail.setAdditionalNote(rs.getString("RET.NOTE"));
				exceptionDetail.setTransactioinDate(transactionDate);
				exceptionDetail.setBranch(rs.getString("RET.BRCD"));
				final String userId = rs.getString("RET.UID");
				exceptionDetail.setUserId(userId);
				final String tranSeq = rs.getString("RET.TSEQ");
				exceptionDetail.setTransactionSeq(tranSeq);
				final List<String> exceptionItemReasons = getExceptionItemReasons(agent, transactionDate, branch, accountNum, userId, tranSeq, dateFormat);
				if (exceptionItemReasons.isEmpty()) {
					exceptionItemReasons.add(rs.getString("EXC.TCMT"));
				}
				exceptionDetail.setExceptionItemReasons(exceptionItemReasons);

				final boolean reviewed = exceptionDetail.isReviewComplete();
				if (reviewed) {
					final boolean decision = rs.getBoolean("RET.RET");
					if (decision) {
						exceptionDetail.setDecision("Return");
					} else {
						exceptionDetail.setDecision("Pay");
					}
				}
				customerExceptionDetails = searchResultList.get(customerNum);
				if (customerExceptionDetails == null) {
					customerExceptionDetails = getCustomerDetails(agent, customerNum);
					populateCustomerRestrictions(agent, customerExceptionDetails);
					populateCustomerStops(agent, customerExceptionDetails);
					if (customerExceptionDetails.getCustomerRestrictionList().size() > 0 || customerExceptionDetails.getCustomerStopList().size() > 0) {
						customerExceptionDetails.setStopsRestrictons(true);
					}
				}
				accountDetail = customerExceptionDetails.getAccountExceptions().get(accountNum);
				if (accountDetail == null) {
					accountDetail = getAccountDetails(agent, accountNum, service, dateFormat);
					populateAccountRestrictions(agent, accountDetail, customerNum);
					populateAccountStops(agent, accountDetail);
					if (accountDetail.getAccountStops().length > 0 || accountDetail.getAccountRestrictionList().size() > 0) {
						customerExceptionDetails.setStopsRestrictons(true);
					}
				}

				exceptionDetail.setExceptionIndex(accountDetail.getExceptions().size() + 1);
				exceptionDetail.setAccountDisplayName(accountDetail.getAccountDisplayName());
				accountDetail.getExceptions().add(exceptionDetail);
				customerExceptionDetails.getAccountExceptions().put(accountNum, accountDetail);
				searchResultList.put(customerNum, customerExceptionDetails);
			}
			service.setStrTotalComplete(String.valueOf(totalComplete));
			service.setStrTotalIncomplete(String.valueOf(totalInComplete));
			service.setStrTotalExceptions(String.valueOf(totalComplete + totalInComplete));
			service.setSearchResultList(searchResultList);

		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "fetchExceptionDetails", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "fetchExceptionDetails", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	public static CustomerExceptionDetails getCustomerDetails(final Agent agent, final String custNumber) {
		final CustomerExceptionDetails customerDetails = new CustomerExceptionDetails();
		final String stmtStr1 = "SELECT CCODE, CO, BOO, HPH, RELCODE, TDB, DAO, BPH, ATN, TLB, ACN, EXCTL12, EXCRL12, EXCPL12, CIFOFF, NAM FROM CIF WHERE ACN = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, custNumber);
			rs = profile.executeQuery();
			StringBuilder strBuilder = new StringBuilder();
			while (rs.next()) {
				strBuilder = new StringBuilder(rs.getString("CCODE"));
				strBuilder.append(" - ");
				final String codeDesc = GlobalManager.getCustomerCodeDescription(rs.getString("CCODE"));
				if (codeDesc != null) {
					strBuilder.append(codeDesc);
				}

				customerDetails.setCustomerCode(strBuilder.toString());
				customerDetails.setCompanyCode(rs.getString("CO"));

				strBuilder = new StringBuilder(rs.getString("BOO"));
				strBuilder.append(" - ");
				final String branchDesc = GlobalManager.getBranchCodeDescription(rs.getString("BOO"));
				if (branchDesc != null) {
					strBuilder.append(branchDesc);
				}
				customerDetails.setBranchOfOwnership(strBuilder.toString());
				customerDetails.setHomePhoneNumber(rs.getString("HPH"));

				final String relationshipStatusCode = rs.getString("RELCODE");
				if (relationshipStatusCode == null) {
					customerDetails.setCustomerRalationshipStatus("");
				} else {
					customerDetails.setCustomerRalationshipStatus(relationshipStatusCode + " - " + GlobalManager.getCustomerRelationshipStatusCodes().get(relationshipStatusCode));
				}

				customerDetails.setTotalDeposits(rs.getString("TDB"));
				customerDetails.setCustomerCreationDate(rs.getDate("DAO"));

				final String customerOff = rs.getString("CIFOFF");
				if (customerOff == null) {
					customerDetails.setCustomerOfficer("");
				} else {
					customerDetails.setCustomerOfficer(customerOff + " - " + getCustomerOfficerDescription(agent, customerOff));
				}

				customerDetails.setWorkPhoneNumber(rs.getString("BPH"));
				customerDetails.setSpecialAttentionMessage(rs.getString("ATN"));
				customerDetails.setTotalLoans(rs.getString("TLB"));
				customerDetails.setCustomerNumber(rs.getString("ACN"));
				customerDetails.setCustomerName(rs.getString("NAM"));
				customerDetails.setTotalExceptions(rs.getString("EXCTL12"));
				customerDetails.setTotalExceptionsReturned(rs.getString("EXCRL12"));
				customerDetails.setTotalExceptionsPaid(rs.getString("EXCPL12"));
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getCustomerDetails", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getCustomerDetails", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return customerDetails;
	}

	private static String getCustomerOfficerDescription(final Agent agent, final String customerOffNumber) {

		final Map<String, String> officerDescriptions = new HashMap<String, String>();

		String description = "";
		final String stmtStr1 = "SELECT FMDESC FROM UTBLOFF WHERE OFF = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, customerOffNumber);
			rs = profile.executeQuery();
			if (rs.next()) {
				description = rs.getString("FMDESC");
				officerDescriptions.put(customerOffNumber, description);
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getCustomerOfficerDescription", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getCustomerOfficerDescription", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return description;
	}

	public static AccountExceptionDetails getAccountDetails(final Agent agent, final String acctNumber, final ExceptionProcessingService service, final SimpleDateFormat dateFormat) {

		AccountExceptionDetails accountDetails;
		accountDetails = new AccountExceptionDetails();
		accountDetails.setAccountNumber(acctNumber);

		fillAccountTypeAndName(agent, accountDetails);

		if (Utility.hasText(accountDetails.getAccountType())) {
			accountDetails.setLoanAccount(checkForLoanAccount(agent, accountDetails.getAccountType()));
		}

		if (accountDetails.isLoanAccount()) {
			getLoanAccountDetails(agent, accountDetails, service, dateFormat);
		} else {
			getDepositAccountDetails(agent, accountDetails, service, dateFormat);
		}
		return accountDetails;
	}

	public static void getDepositAccountDetails(final Agent agent, final AccountExceptionDetails accountDetails, final ExceptionProcessingService service, final SimpleDateFormat dateFormat) {
		final String stmtStr1 = "SELECT ODT, NSFLIM, BAL, BALAVL, EXCTL12, EXCPL12, EXCRL12, RETFCYTD, RETFCPY, RETFWYTD, RETFWPY, BALAVL, CID, FEEITPDYTD, FEEITWVYTD FROM DEP WHERE CID = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, accountDetails.getAccountNumber());
			rs = profile.executeQuery();
			while (rs.next()) {
				accountDetails.setAccountCreationDate(rs.getDate("ODT"));
				accountDetails.setOverdraftLimit(rs.getString("NSFLIM"));
				accountDetails.setLedgerBalance(rs.getString("BAL"));
				accountDetails.setAvailableBalance(rs.getString("BALAVL"));
				accountDetails.setTotalExceptionsForAcct(rs.getString("EXCTL12"));
				accountDetails.setPaidExceptionsForAcct(rs.getString("EXCPL12"));
				accountDetails.setReturnedExceptionsForAcct(rs.getString("EXCRL12"));
				accountDetails.setExceptionsChargedYearToDate(String.valueOf(rs.getDouble("RETFCYTD") + rs.getDouble("FEEITPDYTD")));
				accountDetails.setExceptionsChargedPriorYear(String.valueOf(rs.getDouble("RETFCPY")));
				accountDetails.setWaivedYearToDate(String.valueOf(rs.getDouble("RETFWYTD") + rs.getDouble("FEEITWVYTD")));
				accountDetails.setWaivedPriorYear(String.valueOf(rs.getDouble("RETFWPY")));

				final double total = getTotalAmountOfIncompleteExceptionItems(agent, accountDetails.getAccountNumber(), service.getDefaultDate(), service.getDefaultSource(), dateFormat);
				final double totalBalance = getTotalAmountForProjectBalance(agent, service.getDefaultDate(), accountDetails.getAccountNumber(), dateFormat);

				accountDetails.setTotalOthers(String.valueOf(total));
				accountDetails.setProjectedBalance(getProjectedBalance(agent, accountDetails.getLedgerBalance(), totalBalance));
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getDepositAccountDetails", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getDepositAccountDetails", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	public static void getLoanAccountDetails(final Agent agent, final AccountExceptionDetails accountDetails, final ExceptionProcessingService service, final SimpleDateFormat dateFormat) {
		final String stmtStr1 = "SELECT ODT, AVLBAL, BAL, EXCTL12, EXCPL12, EXCRL12, RETFCYTD, RETFCPY, RETFWYTD, RETFWPY, CID, CRLMT, OLTP FROM LN WHERE CID = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, accountDetails.getAccountNumber());
			rs = profile.executeQuery();
			while (rs.next()) {
				accountDetails.setAccountCreationDate(rs.getDate("ODT"));
				accountDetails.setLedgerBalance(rs.getString("BAL"));
				accountDetails.setAvailableCredit(rs.getString("AVLBAL"));
				accountDetails.setTotalExceptionsForAcct(rs.getString("EXCTL12"));
				accountDetails.setPaidExceptionsForAcct(rs.getString("EXCPL12"));
				accountDetails.setReturnedExceptionsForAcct(rs.getString("EXCRL12"));
				accountDetails.setExceptionsChargedYearToDate(String.valueOf(rs.getDouble("RETFCYTD")));
				accountDetails.setExceptionsChargedPriorYear(String.valueOf(rs.getDouble("RETFCPY")));
				accountDetails.setWaivedYearToDate(String.valueOf(rs.getDouble("RETFWYTD")));
				accountDetails.setWaivedPriorYear(String.valueOf(rs.getDouble("RETFWPY")));

				final double total = getTotalAmountOfIncompleteExceptionItems(agent, accountDetails.getAccountNumber(), service.getDefaultDate(), service.getDefaultSource(), dateFormat);
				final double totalBalance = getTotalAmountForProjectBalance(agent, service.getDefaultDate(), accountDetails.getAccountNumber(), dateFormat);
				accountDetails.setTotalOthers(String.valueOf(total));
				final double overlimitTolerancePercentage = rs.getDouble("OLTP");
				final double creditLimit = rs.getDouble("CRLMT");
				final double overdraftLimitTolerance = overlimitTolerancePercentage * creditLimit;
				accountDetails.setOverlimitTolerance(String.valueOf(overdraftLimitTolerance));
				accountDetails.setProjectedBalance(getProjectedBalance(agent, accountDetails.getLedgerBalance(), totalBalance));
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getLoanAccountDetails", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getLoanAccountDetails", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	public static void fillAccountTypeAndName(final Agent agent, final AccountExceptionDetails accountDetails) {
		String query = "SELECT ACCTNAME, TYPEDES, TYPE, GRP FROM DEP WHERE CID = ?";
		String accountName = null;
		String desc = null;
		String type = null;
		String group = null;
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, accountDetails.getAccountNumber());
			rs = profile.executeQuery();

			while (rs.next()) {
				accountName = rs.getString("ACCTNAME");
				desc = rs.getString("TYPEDES");
				type = rs.getString("TYPE");
				group = rs.getString("GRP");
			}

			if (profile != null) {
				profile.close(rs);
			}

			if (!Utility.hasText(type)) {
				query = "SELECT ACCTNAME, TYPEDES, TYPE, GRP FROM LN WHERE CID = ?";
				profile = new ProfileSQL(agent, query);
				profile.setString(1, accountDetails.getAccountNumber());
				rs = profile.executeQuery();
				while (rs.next()) {
					accountName = rs.getString("ACCTNAME");
					desc = rs.getString("TYPEDES");
					type = rs.getString("TYPE");
					group = rs.getString("GRP");
				}
			}

			if (profile != null) {
				profile.close(rs);
			}

			if (Utility.hasText(type) && !Utility.hasText(accountName)) {
				query = "SELECT DES, GRP FROM PRODCTL WHERE TYPE = ?";
				profile = new ProfileSQL(agent, query);
				profile.setString(1, type);
				rs = profile.executeQuery();
				if (rs.next()) {
					desc = rs.getString("DES");
					group = rs.getString("GRP");
				}
			}

			if (!Utility.hasText(accountName) && !Utility.hasText(desc)) {
				query = "SELECT ACN, DESC1, TYPE FROM GLAD WHERE ACN = ?";
				profile = new ProfileSQL(agent, query);
				profile.setString(1, accountDetails.getAccountNumber());
				rs = profile.executeQuery();
				if (rs.next()) {
					desc = rs.getString("DESC1");
				}
			}

			accountDetails.setAccountName(accountName);
			accountDetails.setAccountDesc(desc);
			accountDetails.setAccountType(type);
			accountDetails.setProductGroup(group);

		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "fillAccountTypeAndName", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "fillAccountTypeAndName", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	public static boolean checkForLoanAccount(final Agent agent, final String type) {
		boolean result = false;
		final String stmtStr1 = "SELECT PRODDFTL.TYPE FROM PRODDFTL WHERE PRODDFTL.TYPE = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, type);
			rs = profile.executeQuery();
			while (rs.next()) {
				if (rs.getString("PRODDFTL.TYPE") != null) {
					result = true;
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "checkForLoanAccount", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "checkForLoanAccount", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return result;
	}

	public static double getTotalAmountForProjectBalance(final Agent agent, final String transactionDate, final String acctNumber, final SimpleDateFormat dateFormat) {
		double tot = 0;
		final String stmtStr1 = "SELECT EXC.TOT, TRN.DC FROM RET, EXC, TRN WHERE RET.TJD = EXC.TJD AND RET.BRCD = EXC.BRCD AND RET.UID = EXC.UID AND RET.TSEQ = EXC.TSEQ AND RET.TJD = ? AND CID = ? AND TRN.ETC = EXC.ETC";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, Utility.toProfileDateString(dateFormat.parse(transactionDate)));
			profile.setString(2, acctNumber);
			rs = profile.executeQuery();
			while (rs.next()) {
				final String total = rs.getString("EXC.TOT");
				String debitOrCredit = rs.getString("TRN.DC");
				if (debitOrCredit == null) {
					debitOrCredit = "";
				}

				if (Utility.hasText(total)) {
					if (debitOrCredit.equals(ApplicationResources.DEBIT)) {
						tot += Double.parseDouble(total);
					} else if (debitOrCredit.equals(ApplicationResources.CREDIT)) {
						tot -= Double.parseDouble(total);
					}
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getTotalAmountForProjectBalance", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getTotalAmountForProjectBalance", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return tot;
	}

	public static double getTotalAmountOfIncompleteExceptionItems(final Agent agent, final String acctNumber, final String transactionDate, final String source, final SimpleDateFormat dateFormat) {
		double tot = 0;
		final String stmtStr1 = "SELECT EXC.TOT FROM RET, EXC WHERE RET.TJD = EXC.TJD AND RET.BRCD = EXC.BRCD AND RET.UID = EXC.UID AND RET.TSEQ = EXC.TSEQ AND RET.REVIEW = 0 AND RET.SRC <> ? AND RET.TJD = ? AND CID = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, source);
			profile.setString(2, Utility.toProfileDateString(dateFormat.parse(transactionDate)));
			profile.setString(3, acctNumber);
			rs = profile.executeQuery();
			while (rs.next()) {
				final String total = rs.getString("EXC.TOT");
				if (Utility.hasText(total)) {
					tot += Double.parseDouble(total);
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getTotalAmountOfIncompleteExceptionItems", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getTotalAmountOfIncompleteExceptionItems", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return tot;
	}

	public static String getProjectedBalance(final Agent agent, final String availableBalance, final double total) {
		double result = 0;
		if (Utility.hasText(availableBalance)) {
			result = Double.parseDouble(availableBalance) - total;
		}
		return String.valueOf(result);
	}

	public static String getExceptionTypeDescription(final String key) {
		String desc = null;
		if (key != null) {
			desc = exceptionTypes.get(key);
			if (desc == null) {
				desc = exceptionTypes.get(key);
			}
		}
		return desc;
	}

	public static Map<String, String> getExceptionPayReturnReasons() {
		return exceptionPayReturnReasons;
	}

	/**
	 * @return the exceptionTypes
	 */
	public static Map<String, String> getExceptionTypes() {
		return exceptionTypes;
	}

	public static Map<String, String> getPayReturnFeeActions() {
		return payReturnFeeActions;
	}

	public static void updateActions(final Agent agent, final String actionCode, final List<ExceptionDetail> exceptions, final String payReason, final String payFeeAction, final String additionNote, final SimpleDateFormat dateTimeFormat) throws SanchezException {
		final String stmtStr1 = "UPDATE RET SET UID2 = ?, PRETRES = ?, FEE = ?, NOTE = ?, REVIEW = ?, RET = ? WHERE CID = ? AND TJD = ? AND BRCD = ? AND UID = ? AND TSEQ = ?";
		ProfileSQL profile = null;
		ExceptionDetail exception;
		try {
			TransactionManager.beginTransaction(agent, true);
			for (int i = 0; i < exceptions.size(); i++) {
				exception = exceptions.get(i);
				profile = new ProfileSQL(agent, stmtStr1);
				profile.setString(1, agent.getUserName());
				profile.setString(2, payReason);
				profile.setString(3, payFeeAction);
				profile.setString(4, additionNote);

				// true - Reviewed
				profile.setString(5, "1");

				// 0 - Pay, 1 - Return
				profile.setString(6, actionCode);

				profile.setString(7, exception.getAccountNumber());

				// Transaction Date
				final String trnDateFmt = exception.getTransactioinDate();
				profile.setString(8, Utility.toProfileDateString(dateTimeFormat.parse(trnDateFmt)));

				// Branch
				profile.setString(9, exception.getBranch());

				// User ID
				profile.setString(10, exception.getUserId());

				// Transaction Sequence
				profile.setString(11, exception.getTransactionSeq());
				profile.executeUpdate();
			}
			TransactionManager.commit(agent);
		} catch (final SQLException se) {
			Logger.error("exception", new String[] {se.getClass().getName(), "ExceptionProcessingManager", "updateActions", se.getMessage()});
			TransactionManager.rollback(agent);
			throw ProfileSQL.toSanchezException(se);
		} catch (final ParseException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "updateActions", e.getMessage()});
			TransactionManager.rollback(agent);
		} finally {
			TransactionManager.endTransaction(agent);
			if (profile != null) {
				profile.close();
			}
		}
	}

	public static void populateAccountRestrictions(final Agent agent, final AccountExceptionDetails accountExceptionDetails, final String customerNumber) throws SanchezException {
		final List<Restriction> accountRestrictionList = new ArrayList<Restriction>(10);
		final String accountRestrictionQuery = "SELECT RFLG, TCMT, STDT, EXDT, RFLG.JDT, RFLG.TIM, RFLG.UID FROM RFLG WHERE CID = ? AND (EXDT IS NULL OR EXDT NOT < :TJD) ORDER BY STDT DESC";

		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			final String accountNumber = accountExceptionDetails.getAccountNumber();
			profile = new ProfileSQL(agent, accountRestrictionQuery);
			profile.setString(1, accountNumber);
			rs = profile.executeQuery();
			while (rs.next()) {
				final Restriction restriction = new Restriction();
				restriction.setRestriction(rs.getString(1));
				restriction.setTellerComment(rs.getString(2));
				restriction.setStartDate(rs.getDate(3));
				restriction.setExpirationDate(rs.getDate(4));
				restriction.setCalendarDate(rs.getDate(5));
				restriction.setTime(rs.getString(6));
				restriction.setUserId(rs.getString(7));
				restriction.setRestrictionType("account");

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					restriction.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
				accountRestrictionList.add(restriction);
			}
			accountExceptionDetails.setAccountRestrictionList(accountRestrictionList);
			accountExceptionDetails.setAvailableRestrictions(GlobalManager.getAccountRestrictions(accountExceptionDetails.getProductGroup()));
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "RestrictionManager", "getAccountRestrictionList", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	public static void populateCustomerStops(final Agent agent, final CustomerExceptionDetails customerExceptionDetails) throws SanchezException {
		final List<Stop> customerStopList = new ArrayList<Stop>();
		final String customerStopQuery = "SELECT ACT,EXP,STPWHY FROM STOP1 WHERE ACN = ? AND (EXP IS NULL OR EXP NOT < :TJD) ORDER BY EXP DESC";

		ProfileSQL profile = null;
		ResultSet rs = null;
		try {

			profile = new ProfileSQL(agent, customerStopQuery);
			profile.setString(1, customerExceptionDetails.getCustomerNumber());
			rs = profile.executeQuery();
			Stop stop;
			while (rs.next()) {
				stop = new Stop();
				stop.setAction(rs.getString("ACT"));
				stop.setExpirationDate(rs.getDate("EXP"));
				stop.setReason(rs.getString("STPWHY"));
				customerStopList.add(stop);
			}

		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "populateAccountStops", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		customerExceptionDetails.setCustomerStopList(customerStopList);
	}

	public static void populateAccountStops(final Agent agent, final AccountExceptionDetails accountExceptionDetails) throws SanchezException {
		final Set<Stop> accountStopList = new TreeSet<Stop>(StopManager.SORT_BY_DATE);
		final String accountNumber = accountExceptionDetails.getAccountNumber();
		StopManager.getAccountStopList(agent, accountNumber, accountStopList);
		StopManager.getCheckStopList(agent, accountNumber, accountStopList);
		StopManager.getAmountStopList(agent, accountNumber, accountStopList);
		StopManager.getAchStopList(agent, accountNumber, accountStopList);
		accountExceptionDetails.setAccountStops(accountStopList.toArray(new Stop[accountStopList.size()]));
	}

	public static void populateCustomerRestrictions(final Agent agent, final CustomerExceptionDetails customerExceptionDetails) throws SanchezException {
		final List<Restriction> restrictionList = new ArrayList<Restriction>(10);
		final String restrictionQuery = "SELECT RFLG, TCMT, STDT, EXDT, JDT, TIM, UID FROM RFLGC WHERE ACN = ? AND (EXDT IS NULL OR EXDT NOT < :TJD) ORDER BY STDT DESC";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, restrictionQuery);
			profile.setString(1, customerExceptionDetails.getCustomerNumber());
			rs = profile.executeQuery();
			while (rs.next()) {
				final Restriction restriction = new Restriction();

				restriction.setRestriction(rs.getString(1));
				restriction.setTellerComment(rs.getString(2));
				restriction.setStartDate(rs.getDate(3));
				restriction.setExpirationDate(rs.getDate(4));
				restriction.setCalendarDate(rs.getDate(5));
				restriction.setTime(rs.getString(6));
				restriction.setUserId(rs.getString(7));

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					restriction.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
				restrictionList.add(restriction);
			}
			customerExceptionDetails.setCustomerRestrictionList(restrictionList);
			customerExceptionDetails.setAvailableRestrictions(GlobalManager.getAccountRestrictions("CIF"));

		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "populateCustomerRestrictions", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	public static void searchAccountNotFound(final Agent agent, final ExceptionProcessingService service, final SimpleDateFormat dateFormat) {

		final String transactionDate = service.getDefaultDate();
		final String source = service.getDefaultSource();
		final String branch = service.getDefaultBranch();
		String status = service.getDefaultStatus();
		if ("all".equals(status)) {
			status = "";
		}

		ProfileSQL profile = null;
		ResultSet rs = null;
		final List<ExceptionDetail> exceptionList = new ArrayList<ExceptionDetail>();
		try {
			ExceptionDetail exceptionDetail;
			int totalComplete = 0;
			int totalInComplete = 0;
			final List<String> additionalConstraints = new ArrayList<String>();
			final StringBuilder query = new StringBuilder("SELECT RET.CID, EXC.TOT, EXC.CK, EXC.EXCTYP, RET.PRETRES, RET.FEE, RET.NOTE, RET.TJD, RET.BRCD, RET.UID, RET.TSEQ, RET.RET, RET.REVIEW, EXC.TCMT, EXC.SPR, RET.CORRECTCID FROM RET, EXC WHERE RET.INVLDFLG = '1' AND RET.TJD = EXC.TJD AND RET.BRCD = EXC.BRCD AND RET.UID = EXC.UID AND RET.TSEQ = EXC.TSEQ AND RET.TJD = ? AND RET.SRC = ?");

			if (Utility.hasText(branch)) {
				query.append(" AND RET.BRCD = ?");
				additionalConstraints.add(branch);
			}

			if (Utility.hasText(status)) {
				query.append(" AND RET.REVIEW = ?");
				additionalConstraints.add(status);
			}

			query.append(" ORDER BY RET.CID");

			profile = new ProfileSQL(agent, query.toString());
			profile.setString(1, Utility.toProfileDateString(dateFormat.parse(transactionDate)));
			profile.setString(2, source);

			for (int offset = 0; offset < additionalConstraints.size(); ++offset) {
				profile.setString(offset + 3, additionalConstraints.get(offset));
			}

			rs = profile.executeQuery();
			while (rs.next()) {
				exceptionDetail = new ExceptionDetail();
				final String invalidAccount = rs.getString("RET.CID");
				exceptionDetail.setInvalidAccountNumber(invalidAccount);
				final String accountNumber = rs.getString("RET.CORRECTCID");
				exceptionDetail.setAccountNumber(accountNumber);
				exceptionDetail.setAmount(rs.getString("EXC.TOT"));
				exceptionDetail.setCheckNumber(rs.getString("EXC.CK"));
				exceptionDetail.setExceptionType(getExceptionTypeDescription(rs.getString("EXC.EXCTYP")));
				final boolean reviewComplete = rs.getBoolean("RET.REVIEW");
				if (reviewComplete) {
					totalComplete++;
				} else {
					totalInComplete++;
				}
				exceptionDetail.setReviewComplete(reviewComplete);
				exceptionDetail.setPayReason(rs.getString("RET.PRETRES"));
				exceptionDetail.setPayFeeAction(rs.getString("RET.FEE"));
				exceptionDetail.setAdditionalNote(rs.getString("RET.NOTE"));
				exceptionDetail.setTransactioinDate(transactionDate);
				exceptionDetail.setBranch(rs.getString("RET.BRCD"));
				final String userId = rs.getString("RET.UID");
				exceptionDetail.setUserId(userId);
				final String tranSeq = rs.getString("RET.TSEQ");
				exceptionDetail.setTransactionSeq(tranSeq);
				final List<String> exceptionItemReasons = getExceptionItemReasons(agent, transactionDate, branch, invalidAccount, userId, tranSeq, dateFormat);
				if (exceptionItemReasons.isEmpty()) {
					String reason = rs.getString("EXC.TCMT");
					if (reason.contains("(%")) {
						reason = reason.substring(0, reason.indexOf("(%")).trim();
					}
					exceptionItemReasons.add(reason);
				}
				exceptionDetail.setExceptionItemReasons(exceptionItemReasons);
				exceptionDetail.setTraceNumber(rs.getString("EXC.SPR"));

				if (Utility.hasText(accountNumber)) {
					final AccountExceptionDetails accountDetails = new AccountExceptionDetails();
					accountDetails.setAccountNumber(accountNumber);
					fillAccountTypeAndName(agent, accountDetails);
					exceptionDetail.setAccountDisplayName(accountDetails.getAccountDisplayName());
				}

				final boolean reviewed = exceptionDetail.isReviewComplete();
				if (reviewed) {
					final boolean decision = rs.getBoolean("RET.RET");
					if (decision) {
						exceptionDetail.setDecision("Return");
					} else {
						exceptionDetail.setDecision("Pay");
					}
				}

				exceptionDetail.setExceptionIndex(exceptionList.size() + 1);
				exceptionList.add(exceptionDetail);

			}
			service.setStrTotalComplete(String.valueOf(totalComplete));
			service.setStrTotalIncomplete(String.valueOf(totalInComplete));
			service.setStrTotalExceptions(String.valueOf(totalComplete + totalInComplete));
			service.setAccountNotFoundResultList(exceptionList);

		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "fetchExceptionDetails", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "fetchExceptionDetails", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	public static void updateAccountNotFoundAction(final Agent agent, final ExceptionDetail exception, final SimpleDateFormat dateFormat) throws SanchezException {
		final String stmtStr1 = "UPDATE RET SET UID2 = ?, PRETRES = ?, FEE = ?, NOTE = ?, REVIEW = ?, RET = ?, RET.CORRECTCID = ? WHERE CID = ? AND TJD = ? AND BRCD = ? AND UID = ? AND TSEQ = ?";
		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, stmtStr1);
			profile.setString(1, agent.getUserName());
			profile.setString(2, exception.getPayReason());
			profile.setString(3, exception.getPayFeeAction());
			profile.setString(4, exception.getAdditionalNote());

			// true - Reviewed
			profile.setString(5, "1");

			// 0 - Pay, 1 - Return
			final String actionCode = exception.getDecision().equals("Pay") ? "0" : "1";
			profile.setString(6, actionCode);

			profile.setString(7, exception.getAccountNumber());

			profile.setString(8, exception.getInvalidAccountNumber());

			// Transaction Date
			final String trnDateFmt = exception.getTransactioinDate();
			profile.setString(9, Utility.toProfileDateString(dateFormat.parse(trnDateFmt)));

			// Branch
			profile.setString(10, exception.getBranch());

			// User ID
			profile.setString(11, exception.getUserId());

			// Transaction Sequence
			profile.setString(12, exception.getTransactionSeq());
			profile.executeUpdate();

		} catch (final SQLException se) {
			Logger.error("exception", new String[] {se.getClass().getName(), "ExceptionProcessingManager", "updateAccountNotFoundAction", se.getMessage()});
			throw ProfileSQL.toSanchezException(se);
		} catch (final ParseException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "updateAccountNotFoundAction", e.getMessage()});

		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	public static List<String> getExceptionItemReasons(final Agent agent, final String trnDate, final String branch, final String acct, final String user, final String tseq, final SimpleDateFormat dateFormat) {
		final List<String> reasons = new ArrayList<String>();
		String stmtStr = "SELECT AMT, OVRLIT.FMDESC FROM EXC1, OVRLIT WHERE OVRLIT.OVR = EXC1.OVR AND TJD = ? AND EXC1.CID = ? AND EXC1.UID = ? AND EXC1.TSEQ = ?";
		final boolean hasBranch = Utility.hasText(branch);

		if (hasBranch) {
			stmtStr += " AND EXC1.BRCD = ?";
		}

		ProfileSQL profile = null;
		ResultSet rs = null;

		try {
			profile = new ProfileSQL(agent, stmtStr);
			profile.setString(1, Utility.toProfileDateString(dateFormat.parse(trnDate)));
			profile.setString(2, acct);
			profile.setString(3, user);
			profile.setString(4, tseq);
			if (hasBranch) {
				profile.setString(5, branch);
			}
			rs = profile.executeQuery();

			while (rs.next()) {
				final String amt = rs.getString("AMT");
				String reason = rs.getString("OVRLIT.FMDESC");
				if (reason.contains("(%")) {
					reason = reason.substring(0, reason.indexOf("(%")).trim();
				}
				reason = replaceVariableValue(amt, reason);
				reasons.add(reason);
			}

			String excppStr = "SELECT AMT, OVRLIT.FMDESC FROM EXCPP, OVRLIT WHERE OVRLIT.OVR = EXCPP.OVR AND TJD = ? AND EXCPP.CID = ? AND EXCPP.OUID = ? AND EXCPP.OTSEQ = ?";
			if (hasBranch) {
				excppStr += " AND EXCPP.BRCD = ?";
			}

			profile = new ProfileSQL(agent, excppStr);
			profile.setString(1, Utility.toProfileDateString(dateFormat.parse(trnDate)));
			profile.setString(2, acct);
			profile.setString(3, user);
			profile.setString(4, tseq);

			if (hasBranch) {
				profile.setString(5, branch);
			}

			rs = profile.executeQuery();

			while (rs.next()) {
				final String amt = rs.getString("AMT");
				String reason = rs.getString("OVRLIT.FMDESC");
				if (reason.contains("(%")) {
					reason = reason.substring(0, reason.indexOf("(%")).trim();
				}
				reason = reason + " - PP";
				reason = replaceVariableValue(amt, reason);
				reasons.add(reason);
			}

		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getExceptionItemReasonDescription", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} catch (final Exception e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "ExceptionProcessingManager", "getExceptionItemReasonDescription", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return reasons;
	}

	private static String replaceVariableValue(final String amt, final String reason) {
		String retString = reason;
		if (reason.contains("<") && reason.contains(">") && Utility.hasText(amt)) {
			final StringBuilder sb = new StringBuilder();
			sb.append(reason.substring(0, reason.indexOf('<')));
			sb.append(amt);
			sb.append(reason.substring(reason.indexOf('>') + 1));
			retString = sb.toString();
		}
		return retString;
	}

}
