package com.fisglobal.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fisglobal.base.Application;
import com.fisglobal.base.ApplicationResources;
import com.fisglobal.base.Logger;
import com.fisglobal.base.SanchezException;
import com.fisglobal.bean.Agent;
import com.fisglobal.bean.AlertService;
import com.fisglobal.bean.CommunicationMethod;
import com.fisglobal.bean.DepositAccount;
import com.fisglobal.bean.LoanAccount;
import com.fisglobal.bean.SMSMobileProvider;
import com.fisglobal.bean.User;
import com.fisglobal.host.ProfileSQL;
import com.fisglobal.utils.Utility;

public final class AlertServiceManager extends BaseManager {

	private AlertServiceManager() {

	}

	/**
	 * Populate alert information messages where ALRTCAT=2 OR ALRTCAT=6
	 * AND ALRTPROC is null to add the general information message.
	 * 
	 * @return map<String, String>
	 */
	public static List<AlertService> populateAlertInformationMessage(final Agent agent, final String customerNumber) throws SanchezException {

		final List<AlertService> alertInformationMessageList = new ArrayList<AlertService>();
		final String query = "SELECT CIFONALRT.ALRTID, UTBLALRTTYPE.FMDESC, CIFONALRT.REFNO FROM CIFONALRT, UTBLALRTTYPE WHERE CIFONALRT.ACN = ? AND CIFONALRT.ALRTID = UTBLALRTTYPE.ALRTID AND UTBLALRTTYPE.ALRTCAT = 6 AND UTBLALRTTYPE.ALRTPROC IS NULL AND (UTBLALRTTYPE.ALRTEXPDT >= ? OR UTBLALRTTYPE.ALRTEXPDT IS NULL)";

		ProfileSQL psql = null;
		ResultSet rs = null;
		try {
			psql = new ProfileSQL(agent, query);
			psql.setString(1, customerNumber);
			psql.setString(2, Utility.toProfileString(Utility.getSystemDate()));
			rs = psql.executeQuery();
			while (rs.next()) {
				final AlertService alertService = new AlertService();
				alertService.setDescription(rs.getString("UTBLALRTTYPE.FMDESC"));
				alertService.setReferenceNumber(rs.getString("CIFONALRT.REFNO"));
				alertInformationMessageList.add(alertService);

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					alertService.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "fillAlertInfoMessages", e.getMessage()});
		} finally {
			if (psql != null) {
				psql.close(rs);
			}
		}
		return alertInformationMessageList;
	}

	/**
	 * Retrieves all the alerts defined by the Institution
	 * 
	 * @param agent
	 * @return list<AlertService>
	 * @throws SanchezException
	 */
	public static List<AlertService> populateInstitutionAlerts(final Agent agent) throws SanchezException {

		final List<AlertService> alertsList = new ArrayList<AlertService>();
		final String sqlQuery = "SELECT UTBLALRTTYPE.ALRTID, UTBLALRTTYPE.ALRTTYPE, UTBLALRTTYPE.EFDT, UTBLALRTTYPE.ALRTCAT, UTBLALRTTYPE.ALRTCHAN, UTBLALRTTYPE.ALRTADDL, UTBLALRTTYPE.ALRTEXPDT, UTBLALRTTYPE.FMDESC, UTBLALRTTYPE.ALRTDDM FROM UTBLALRTTYPE, STBLALRTTYPE WHERE UTBLALRTTYPE.ALRTTYPE = STBLALRTTYPE.ALRTTYPE AND STBLALRTTYPE.ALRTSETUP = '1'";

		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, sqlQuery);
			rs = profile.executeQuery();
			while (rs.next()) {
				final AlertService alertService = new AlertService();
				alertService.setAlertId(rs.getString("UTBLALRTTYPE.ALRTID"));
				alertService.setAlertType(rs.getString("UTBLALRTTYPE.ALRTTYPE"));
				alertService.setEffectiveDate(rs.getDate("UTBLALRTTYPE.EFDT"));
				alertService.setCateoryId(rs.getString("UTBLALRTTYPE.ALRTCAT"));
				alertService.setChannnelOption(rs.getBoolean("UTBLALRTTYPE.ALRTCHAN"));
				alertService.setAlertDescription(rs.getString("UTBLALRTTYPE.ALRTADDL"));
				alertService.setExpiryDate(rs.getDate("UTBLALRTTYPE.ALRTEXPDT"));
				alertService.setDescription(rs.getString("UTBLALRTTYPE.FMDESC"));
				alertService.setDefaultMethod(rs.getString("UTBLALRTTYPE.ALRTDDM"));
				alertsList.add(alertService);

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					alertService.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "populateInstitutionAlerts", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return alertsList;
	}

	/**
	 * Retrieves all the alerts defined by the customer
	 * 
	 * @param agent
	 * @param customerNumber
	 * @return list<AlertService>
	 * @throws SanchezException
	 */
	public static List<AlertService> getCustomerDefinedAlerts(final Agent agent, final String customerNumber) throws SanchezException {

		final List<AlertService> alertsList = new ArrayList<AlertService>();

		final String sqlQuery = "SELECT CIFALTPAR.CID, CIFALTPAR.ALRTID, CIFALTPAR.ALRTSEQ, UTBLALRTTYPE.ALRTTYPE, CIFALTPAR.MINAMT, CIFALTPAR.MAXAMT, CIFALTPAR.ALRTOCC, CIFALTPAR.TRNAMT, CIFALTPAR.CHKNUM, CIFALTPAR.HCHKNUM, CIFALTPAR.TOLPERCENT, CIFALTPAR.CRDNUM, CIFALTPAR.MAXCOUNT FROM CIFALTPAR, UTBLALRTTYPE WHERE CIFALTPAR.ACN = ? AND CIFALTPAR.ALRTID = UTBLALRTTYPE.ALRTID AND CIFALTPAR.EXPDATE IS NULL";

		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, sqlQuery);
			profile.setString(1, customerNumber);
			rs = profile.executeQuery();
			while (rs.next()) {
				final AlertService alertService = new AlertService();
				alertService.setAccountNumber(rs.getString("CIFALTPAR.CID"));
				alertService.setAlertId(rs.getString("CIFALTPAR.ALRTID"));
				alertService.setAlertSequence(rs.getString("CIFALTPAR.ALRTSEQ"));
				alertService.setAlertType(rs.getString("UTBLALRTTYPE.ALRTTYPE"));
				alertService.setMinAmount(rs.getBigDecimal("CIFALTPAR.MINAMT"));
				alertService.setMaxAmount(rs.getBigDecimal("CIFALTPAR.MAXAMT"));
				alertService.setOccurrence(rs.getString("CIFALTPAR.ALRTOCC"));
				alertService.setAmount(rs.getBigDecimal("CIFALTPAR.TRNAMT"));
				alertService.setCheckNumber(rs.getString("CIFALTPAR.CHKNUM"));
				alertService.setToCheckNumber(rs.getString("CIFALTPAR.HCHKNUM"));
				alertService.setTolpercent(rs.getString("CIFALTPAR.TOLPERCENT"));
				alertService.setCardNumber(rs.getString("CIFALTPAR.CRDNUM"));
				alertService.setDailyCardUsageLimit(rs.getString("CIFALTPAR.MAXCOUNT"));
				alertService.setCustomerNumber(customerNumber);

				if (rs.getString("CIFALTPAR.CID") != null) {
					populateAccountName(agent, alertService);
				}

				alertsList.add(alertService);

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					alertService.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "getCustomerDefinedAlerts", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return alertsList;
	}

	public static AlertService getUserAlertDetails(final Agent agent, final AlertService alertService) throws SanchezException {

		final String sqlQuery = "SELECT AMOUNT, AMTRANGE, CHKNUM, CRDNUM, MAXCOUNT, TOLPERCENT, MULTIPAR FROM UTBLALRTTYPE WHERE ALRTID = ?";

		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, sqlQuery);
			profile.setString(1, alertService.getAlertId());
			rs = profile.executeQuery();
			while (rs.next()) {
				alertService.setAmountFlag(rs.getBoolean("AMOUNT"));
				alertService.setAmountRangeFlag(rs.getBoolean("AMTRANGE"));
				alertService.setCheckNumberFlag(rs.getBoolean("CHKNUM"));
				alertService.setCardNumberFlag(rs.getBoolean("CRDNUM"));
				alertService.setMaximumCountFlag(rs.getBoolean("MAXCOUNT"));
				alertService.setTolerancePercentageFlag(rs.getBoolean("TOLPERCENT"));
				alertService.setAllowMultipleAlertParameters(rs.getBoolean("MULTIPAR"));

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					alertService.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "populateInstitutionAlerts", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return alertService;
	}

	/**
	 * To Save the alert details into CIFALTPAR table.
	 * 
	 * @param agent
	 * @param alertService
	 * @throws SanchezException
	 */
	public static void addAlertDetail(final Agent agent, final AlertService alertService) throws SanchezException {

		final String insertStmt = "INSERT INTO CIFALTPAR (ACN, ALTTRH, CID, ALRTID, ALRTOCC, CHKNUM, MAXAMT, MINAMT, TRNAMT, TOLPERCENT, MAXCOUNT, CRDNUM, ALRTSEQ, HCHKNUM) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,CIFALTPAR.NEXTVAL,?)";
		final String alertSeq = "SELECT ALRTSEQ FROM CIFALTPAR WHERE ACN=? AND ALRTID=? ";
		ProfileSQL profile1 = null;
		ProfileSQL profile2 = null;
		ResultSet rs = null;

		try {
			TransactionManager.beginTransaction(agent, true);

			profile1 = new ProfileSQL(agent, alertSeq);
			profile1.setString(1, Utility.toProfileString(alertService.getCustomerNumber()));
			profile1.setString(2, Utility.toProfileString(alertService.getAlertId()));
			rs = profile1.executeQuery();

			int alertSequence = 0;
			while (rs.next()) {
				if (Integer.parseInt(rs.getString("ALRTSEQ")) > alertSequence) {
					alertSequence = Integer.parseInt(rs.getString("ALRTSEQ"));
				}
			}

			alertSequence++;

			profile2 = new ProfileSQL(agent, insertStmt);

			profile2.setString(1, Utility.toProfileString(alertService.getCustomerNumber()));
			profile2.setString(2, Utility.toProfileString(alertService.getThershold()));
			profile2.setString(3, Utility.toProfileString(alertService.getAccountNumber()));
			profile2.setString(4, Utility.toProfileString(alertService.getAlertId()));

			if (alertService.getAlertId().equalsIgnoreCase(ApplicationResources.ACCOUNT_BALANCE_TOLERANCE)) {
				profile2.setString(5, Utility.toProfileString(alertService.getOccurrence()));
			} else {
				profile2.setString(5, "");
			}

			if (alertService.isCheckNumberFlag()) {
				profile2.setString(6, Utility.toProfileString(alertService.getCheckNumber()));
			} else {
				profile2.setString(6, "");
			}

			if (alertService.isAmountRangeFlag()) {
				profile2.setString(7, Utility.toProfileString(alertService.getMaxAmount()));
				profile2.setString(8, Utility.toProfileString(alertService.getMinAmount()));
			} else {
				profile2.setString(7, "");
				profile2.setString(8, "");
			}

			if (alertService.isAmountFlag()) {
				profile2.setString(9, Utility.toProfileString(alertService.getAmount()));
			} else {
				profile2.setString(9, "");
			}

			if (alertService.isTolerancePercentageFlag()) {
				profile2.setString(10, Utility.toProfileString(alertService.getTolpercent()));
			} else {
				profile2.setString(10, "");
			}

			if (alertService.isMaximumCountFlag()) {
				profile2.setString(11, Utility.toProfileString(alertService.getDailyCardUsageLimit()));
			} else {
				profile2.setString(11, "");
			}

			if (alertService.isCardNumberFlag()) {
				profile2.setString(12, Utility.toProfileString(alertService.getCardNumber()));
			} else {
				profile2.setString(12, "");
			}

			if (alertService.isCheckNumberFlag() && (alertService.getToCheckNumber() != null && alertService.getToCheckNumber().trim().length() > 0)) {
				profile2.setString(13, Utility.toProfileString(alertService.getToCheckNumber()));
			} else {
				profile2.setString(13, "");
			}

			profile2.executeUpdate();

			// Add the selected delivery address options
			addDeliveryMethods(agent, alertService, alertSequence);

			TransactionManager.commit(agent);
		} catch (final SQLException e) {
			TransactionManager.rollback(agent);
			Logger.warn("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "addAlertDetail", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			TransactionManager.endTransaction(agent);
			if (profile1 != null) {
				profile1.close(rs);
			}
			if (profile2 != null) {
				profile2.close();
			}
		}
	}

	/**
	 * To update the Alert Details into CIFALTPAR table.
	 * 
	 * @param agent
	 * @param alertService
	 * @throws SanchezException
	 */
	public static void updateAlert(final Agent agent, final AlertService alertService) throws SanchezException {

		final String updateStmt = "UPDATE CIFALTPAR SET ALTTRH = ?, MINAMT = ?, MAXAMT = ?, ALRTOCC = ?, TRNAMT = ?, CHKNUM = ?, TOLPERCENT = ?, CRDNUM = ?, MAXCOUNT = ?, HCHKNUM = ?, CID = ? WHERE ALRTID = ? AND ALRTSEQ = ? AND ACN = ?";

		ProfileSQL profile = null;
		try {
			TransactionManager.beginTransaction(agent, true);
			profile = new ProfileSQL(agent, updateStmt);

			profile.setString(1, alertService.getThershold());

			if (alertService.isAmountRangeFlag()) {
				profile.setString(2, Utility.toProfileString(alertService.getMinAmount()));
				profile.setString(3, Utility.toProfileString(alertService.getMaxAmount()));
			} else {
				profile.setString(2, "");
				profile.setString(3, "");
			}

			if (alertService.getAlertId().equalsIgnoreCase(ApplicationResources.ACCOUNT_BALANCE_TOLERANCE)) {
				profile.setString(4, Utility.toProfileString(alertService.getOccurrence()));
			} else {
				profile.setString(4, "");
			}

			if (alertService.isAmountFlag()) {
				profile.setString(5, Utility.toProfileString(alertService.getAmount()));
			} else {
				profile.setString(5, "");
			}

			if (alertService.isCheckNumberFlag()) {
				profile.setString(6, Utility.toProfileString(alertService.getCheckNumber()));
			} else {
				profile.setString(6, "");
			}

			if (alertService.isTolerancePercentageFlag()) {
				profile.setString(7, Utility.toProfileString(alertService.getTolpercent()));
			} else {
				profile.setString(7, "");
			}

			if (alertService.isCardNumberFlag()) {
				profile.setString(8, Utility.toProfileString(alertService.getCardNumber()));
			} else {
				profile.setString(8, "");
			}

			if (alertService.isMaximumCountFlag()) {
				profile.setString(9, Utility.toProfileString(alertService.getDailyCardUsageLimit()));
			} else {
				profile.setString(9, "");
			}

			if (alertService.isCheckNumberFlag()) {
				profile.setString(10, Utility.toProfileString(alertService.getToCheckNumber()));
			} else {
				profile.setString(10, "");
			}

			profile.setString(11, Utility.toProfileString(alertService.getAccountNumber()));
			profile.setString(12, Utility.toProfileString(alertService.getAlertId()));
			profile.setString(13, Utility.toProfileString(alertService.getAlertSequence()));
			profile.setString(14, Utility.toProfileString(alertService.getCustomerNumber()));

			profile.executeUpdate();

			// Delete the previous delivery address options.
			deleteDeliveryMethods(agent, alertService);

			// Update the selected delivery address options.
			updateDeliveryMethods(agent, alertService);

			TransactionManager.commit(agent);
		} catch (final SQLException e) {
			TransactionManager.rollback(agent);
			Logger.warn("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "updateAlert", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			TransactionManager.endTransaction(agent);
			if (profile != null) {
				profile.close();
			}
		}
	}

	/**
	 * To edit or view alert detail from table CIFALTPAR.
	 * 
	 * @param agent
	 * @param alertService
	 * @return AlertService
	 * @throws SanchezException
	 */
	public static AlertService editOrViewAlert(final Agent agent, final AlertService alertService) throws SanchezException {

		final String sqlQuery = "SELECT MINAMT, MAXAMT, ALRTOCC, TRNAMT, CHKNUM, HCHKNUM, ALTTRH, TOLPERCENT, ALRTSEQ, CRDNUM, MAXCOUNT FROM CIFALTPAR WHERE ACN = ? AND ALRTSEQ = ? AND ALRTID = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, sqlQuery);
			profile.setString(1, alertService.getCustomerNumber());
			profile.setString(2, alertService.getAlertSequence());
			profile.setString(3, alertService.getAlertId());
			rs = profile.executeQuery();
			while (rs.next()) {
				alertService.setMinAmount(rs.getBigDecimal("MINAMT"));
				alertService.setMaxAmount(rs.getBigDecimal("MAXAMT"));
				alertService.setOccurrence(rs.getString("ALRTOCC"));
				alertService.setAmount(rs.getBigDecimal("TRNAMT"));
				alertService.setCheckNumber(rs.getString("CHKNUM"));
				alertService.setToCheckNumber(rs.getString("HCHKNUM"));
				alertService.setThershold(rs.getString("ALTTRH"));
				alertService.setTolpercent(rs.getString("TOLPERCENT"));
				alertService.setAlertSequence(rs.getString("ALRTSEQ"));
				alertService.setCardNumber(rs.getString("CRDNUM"));
				alertService.setDailyCardUsageLimit(rs.getString("MAXCOUNT"));

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					alertService.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "editOrViewAlert", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return alertService;
	}

	/**
	 * To delete a record from CIFALTPAR Table.
	 * 
	 * @param agent
	 * @param customerNumber
	 * @param accountNumber
	 * @param alertId
	 * @throws SanchezException
	 */
	public static void deleteAlert(final Agent agent, final String customerNumber, final String sequence, final String alertId) throws SanchezException {

		final String alertDelete = "DELETE FROM CIFALTPAR WHERE ACN = ? AND ALRTSEQ = ? AND ALRTID = ?";
		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, alertDelete);
			profile.setString(1, customerNumber);
			profile.setString(2, sequence);
			profile.setString(3, alertId);
			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "deleteAlert", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	/**
	 * To retrieve a alert channel info for an alert from UTBLALRTTYPE table.
	 * 
	 * @param agent
	 * @param alertId
	 * @return String
	 * @throws SanchezException
	 */
	private static String getAlertDetails(final Agent agent, final String alertId) throws SanchezException {

		final String sqlQuery = "SELECT ALRTCHAN FROM UTBLALRTTYPE WHERE ALRTID = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		String alertChannel = "";
		try {
			profile = new ProfileSQL(agent, sqlQuery);
			profile.setString(1, alertId);
			rs = profile.executeQuery();
			while (rs.next()) {
				alertChannel = Utility.toProfileString(rs.getBoolean("ALRTCHAN"));
			}
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "getAlertDetails", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return alertChannel;
	}

	/**
	 * To populate the Account List.
	 * 
	 * @param agent
	 * @param user
	 * @param alertId
	 * @return
	 * @throws SanchezException
	 */
	public static LinkedHashMap<String, String> populateAccountList(final Agent agent, final User user, final String alertId) throws SanchezException {

		final LinkedHashMap<String, String> accountList = new LinkedHashMap<String, String>();
		final DepositAccount[] depositAccounts = user.getDepositAccounts();
		final LoanAccount[] loanAccounts = user.getLoanAccounts();
		final String alertChannel = getAlertDetails(agent, alertId);

		if ("DEP".equalsIgnoreCase(alertChannel)) {
			for (int i = 0; i < depositAccounts.length; ++i) {
				accountList.put(depositAccounts[i].getNumber(), depositAccounts[i].getDisplayName());
			}
		} else if ("LN".equalsIgnoreCase(alertChannel)) {
			for (int i = 0; i < loanAccounts.length; ++i) {
				accountList.put(loanAccounts[i].getNumber(), loanAccounts[i].getDisplayName());
			}
		} else {
			for (int i = 0; i < depositAccounts.length; ++i) {
				accountList.put(depositAccounts[i].getNumber(), depositAccounts[i].getDisplayName());
			}
			for (int i = 0; i < loanAccounts.length; ++i) {
				accountList.put(loanAccounts[i].getNumber(), loanAccounts[i].getDisplayName());
			}
		}
		return accountList;
	}

	/**
	 * To get the SMS Mobile Provider Details from UTBLMOBADD Table
	 * 
	 * @param agent
	 * @return List<SMSMobileProvider>
	 * @throws SanchezException
	 */
	public static List<SMSMobileProvider> getSmsMobileProviderDetails(final Agent agent) throws SanchezException {

		final String query = "SELECT MOBPROID, ADDFMT, FMDESC FROM UTBLMOBADD";
		final List<SMSMobileProvider> providerList = new ArrayList<SMSMobileProvider>();
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, query);
			rs = profile.executeQuery();
			while (rs.next()) {
				final SMSMobileProvider provider = new SMSMobileProvider();
				provider.setProviderId(rs.getString("MOBPROID"));
				provider.setAddresFormat(rs.getString("ADDFMT"));
				provider.setDescription(rs.getString("FMDESC"));
				providerList.add(provider);

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					provider.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "getSmsMobileProviderDetails", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return providerList;
	}

	/**
	 * To populate the Alert messages for a customer from CIFONALRT Table
	 * 
	 * @param agent
	 * @param customerNumber
	 * @return list<AlertService>
	 * @throws SanchezException
	 */
	public static List<AlertService> populateAlertMessage(final Agent agent, final String customerNumber) throws SanchezException {

		final List<AlertService> alertMessageList = new ArrayList<AlertService>();
		final String sqlQuery = "SELECT CIFONALRT.REFNO, CIFONALRT.ALRTCAT, CIFONALRT.ALRTID, CIFONALRT.DELMTHD, CIFONALRT.STATDATE, CIFONALRT.EXPDT, UTBLALRTTYPE.ALRTTYPE FROM CIFONALRT, UTBLALRTTYPE WHERE CIFONALRT.ACN = ? AND CIFONALRT.ALRTID = UTBLALRTTYPE.ALRTID AND (CIFONALRT.STATUS = '1' OR CIFONALRT.STATUS = '2') AND CIFONALRT.ALRTCAT <> '6' AND (CIFONALRT.EXPDT >= ? OR CIFONALRT.EXPDT IS NULL)";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, sqlQuery);
			profile.setString(1, customerNumber);
			profile.setString(2, Utility.toProfileString(Utility.getSystemDate()));
			rs = profile.executeQuery();
			while (rs.next()) {
				final AlertService alertService = new AlertService();
				alertService.setReferenceNumber(rs.getString("CIFONALRT.REFNO"));
				alertService.setCateoryId(rs.getString("CIFONALRT.ALRTCAT"));
				alertService.setAlertId(rs.getString("CIFONALRT.ALRTID"));
				alertService.setDefaultMethod(rs.getString("CIFONALRT.DELMTHD"));
				alertService.setStatusDate(rs.getDate("CIFONALRT.STATDATE"));
				alertService.setExpiryDate(rs.getDate("CIFONALRT.EXPDT"));
				alertService.setAlertType(rs.getString("UTBLALRTTYPE.ALRTTYPE"));
				alertMessageList.add(alertService);

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					alertService.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "populateAlertMessage", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return alertMessageList;
	}

	/**
	 * CIFONALRT.ALRTMSG is a memo field. We have to query the memo field
	 * separately due to a JDBC driver restriction
	 * 
	 * @param referenceNumber
	 * @return String
	 * @throws SanchezException
	 */
	private static String getAlertMessage(final Agent agent, final String referenceNumber, final String customerNumber) throws SanchezException {
		String alertMessageString = null;
		final String sqlMemoQuery = "SELECT ALRTMSG FROM CIFONALRT WHERE REFNO = ? AND ACN = ?";
		ProfileSQL memoProfile = null;
		ResultSet memoRs = null;

		// We have to query the memo field separately due to a JDBC driver restriction
		try {
			memoProfile = new ProfileSQL(agent, sqlMemoQuery);
			memoProfile.setString(1, referenceNumber);
			memoProfile.setString(2, customerNumber);
			memoRs = memoProfile.executeQuery();
			if (memoRs.next()) {
				alertMessageString = memoRs.getString("CIFONALRT.ALRTMSG");
			}
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "getAlertMessage", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (memoProfile != null) {
				memoProfile.close(memoRs);
			}
		}
		return alertMessageString;
	}

	/**
	 * To populate the Customer Communication Address for an Alert Message.
	 * 
	 * @param agent
	 * @param alertService
	 * @throws SanchezException
	 */
	private static void populateCustomerDeliveryAddress(final Agent agent, final AlertService alertService) throws SanchezException {

		final String query = "SELECT COMADD FROM CIFCOMADD WHERE ACN = ? AND COMMETID = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, alertService.getCustomerNumber());
			profile.setString(2, alertService.getDefaultMethod());
			rs = profile.executeQuery();
			while (rs.next()) {
				alertService.setDeliveryAddress(rs.getString("COMADD"));
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "populateCustomerDeliveryAddress", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	public static Map<String, Map<String, String>> populateCardNumbers(final Agent agent, final String customerNumber) throws SanchezException {
		final Map<String, Map<String, String>> cardNumbers = new LinkedHashMap<String, Map<String, String>>();

		final String cardQuery = "SELECT CRDNUM, CID FROM CRD, CRDGRP WHERE CRD.CRDNUM = CRDGRP.CRDNUM AND CRD.CRDTYP = CRDGRP.CRDTYP AND ACN = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, cardQuery);
			profile.setString(1, customerNumber);
			rs = profile.executeQuery();
			while (rs.next()) {
				final String accountNumber = rs.getString("CID");
				if (!cardNumbers.containsKey(accountNumber)) {
					cardNumbers.put(accountNumber, new LinkedHashMap<String, String>());
				}
				final String crdNum = rs.getString("CRDNUM");
				final String value = GlobalManager.getMaskedFieldValue(agent.getUserClass(), "CRD_CRDNUM", crdNum);
				if (value == null) {
					cardNumbers.get(accountNumber).put(crdNum, crdNum);
				} else {
					cardNumbers.get(accountNumber).put(value, crdNum);
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "populateCardNumbers", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return cardNumbers;
	}

	/**
	 * To get an alert message details from the History
	 * CIFONALRT AND CIFCOMADD)table
	 * 
	 * @param agent
	 * @param referenceNumber
	 * @param customerNumber
	 * @return AlertService
	 * @throws SanchezException
	 */
	public static AlertService getAlertMessageDetail(final Agent agent, final String referenceNumber, final String customerNumber) throws SanchezException {

		AlertService alertService = null;
		final String sqlQuery = "SELECT ALRTCAT, ALRTID, CID, DELMTHD, STATDATE FROM CIFONALRT WHERE REFNO = ? AND ACN = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, sqlQuery);
			profile.setString(1, referenceNumber);
			profile.setString(2, customerNumber);
			rs = profile.executeQuery();
			while (rs.next()) {
				alertService = new AlertService();
				alertService.setCustomerNumber(customerNumber);
				alertService.setReferenceNumber(referenceNumber);
				alertService.setCateoryId(rs.getString("ALRTCAT"));
				alertService.setAlertId(rs.getString("ALRTID"));

				final String number = GlobalManager.getMaskedFieldValue(agent.getUserClass(), "ACN_CID", rs.getString("CID"));
				if (number != null) {
					alertService.setAccountNumber(number);
				} else {
					alertService.setAccountNumber(rs.getString("CID"));
				}

				alertService.setDefaultMethod(rs.getString("DELMTHD"));
				alertService.setStatusDate(rs.getDate("STATDATE"));
				alertService.setAlertMessage(getAlertMessage(agent, referenceNumber, customerNumber));

				populateCustomerDeliveryAddress(agent, alertService);

				if (rs.getString("CID") != null) {
					populateAccountName(agent, alertService);
				}

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					alertService.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "getAlertMessageDetail", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return alertService;
	}

	/**
	 * To select alert offer category defined by the institution
	 * from UTBLALRTTYPE table where ALRTCAT=6 and ALRTPROC is not null
	 * to avoid the adding of general messages under Offer.
	 * 
	 * @param agent
	 * @return list<AlertService>
	 * @throws SanchezException
	 */
	public static List<AlertService> populateAlertOfferMessages(final Agent agent, final String customerNumber) throws SanchezException {

		final List<AlertService> alertOfferList = new ArrayList<AlertService>();

		final String sqlQuery = "SELECT CIFONALRT.ALRTID, UTBLALRTTYPE.EFDT, UTBLALRTTYPE.ALRTCAT, UTBLALRTTYPE.FMDESC, UTBLALRTTYPE.ALRTEXPDT, UTBLALRTTYPE.ALRTPROC, CIFONALRT.REFNO FROM CIFONALRT, UTBLALRTTYPE WHERE CIFONALRT.ACN = ? AND CIFONALRT.ALRTID = UTBLALRTTYPE.ALRTID AND UTBLALRTTYPE.ALRTCAT = 6 AND UTBLALRTTYPE.ALRTPROC IS NOT NULL AND (UTBLALRTTYPE.ALRTEXPDT >= ? OR UTBLALRTTYPE.ALRTEXPDT IS NULL)";

		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, sqlQuery);
			profile.setString(1, customerNumber);
			profile.setString(2, Utility.toProfileString(Utility.getSystemDate()));
			rs = profile.executeQuery();
			while (rs.next()) {
				final AlertService alertService = new AlertService();
				alertService.setAlertId(rs.getString("CIFONALRT.ALRTID"));
				alertService.setEffectiveDate(rs.getDate("UTBLALRTTYPE.EFDT"));
				alertService.setCateoryId(rs.getString("UTBLALRTTYPE.ALRTCAT"));
				alertService.setDescription(rs.getString("UTBLALRTTYPE.FMDESC"));
				alertService.setExpiryDate(rs.getDate("UTBLALRTTYPE.ALRTEXPDT"));
				alertService.setPromotionCode(rs.getString("UTBLALRTTYPE.ALRTPROC"));
				alertService.setReferenceNumber(rs.getString("CIFONALRT.REFNO"));
				alertOfferList.add(alertService);

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					alertService.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "populateAlertOfferMessages", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return alertOfferList;
	}

	/**
	 * To view all the alerts that have been generated for a particular
	 * Customer from CIFONALRT Table.
	 * 
	 * @param agent
	 * @param customerNumber
	 * @return list<AlertService>
	 * @throws SanchezException
	 */
	public static List<AlertService> populateAlertHistory(final Agent agent, final String customerNumber) throws SanchezException {

		final List<AlertService> alertHistoryList = new ArrayList<AlertService>();
		final String sqlQuery = "SELECT STATDATE, ALRTCAT, ALRTID, STATUS, EXPDT, REFNO FROM CIFONALRT WHERE ACN = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, sqlQuery);
			profile.setString(1, customerNumber);
			rs = profile.executeQuery();
			while (rs.next()) {
				final AlertService alertService = new AlertService();
				alertService.setStatusDate(rs.getDate("STATDATE"));
				alertService.setCateoryId(rs.getString("ALRTCAT"));
				alertService.setAlertId(rs.getString("ALRTID"));
				alertService.setAlertStatus(rs.getString("STATUS"));
				alertService.setExpiryDate(rs.getDate("EXPDT"));
				alertService.setReferenceNumber(rs.getString("REFNO"));
				alertHistoryList.add(alertService);

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					alertService.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "populateAlertHistory", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return alertHistoryList;
	}

	/**
	 * To get the alert History details for a particular
	 * alert from CIFONALRT Table
	 * 
	 * @param agent
	 * @param alertService
	 * @return AlertService
	 * @throws SanchezException
	 */
	public static AlertService getAlertHistoryDetail(final Agent agent, final AlertService alertService, final String refNumber, final String customerNumber) throws SanchezException {

		final String sqlQuery = "SELECT CIFONALRT.ACN, CIFONALRT.STATDATE, CIFONALRT.STATUS, CIFONALRT.CID, CIFONALRT.ALRTCAT, CIFONALRT.ALRTID, UTBLALRTTYPE.ALRTTYPE FROM CIFONALRT, UTBLALRTTYPE WHERE REFNO = ? AND ACN = ? AND CIFONALRT.ALRTID = UTBLALRTTYPE.ALRTID";
		final String sqlMemoQuery = "SELECT ALRTMSG FROM CIFONALRT WHERE REFNO = ? AND ACN = ?";
		ProfileSQL profile = null;
		ProfileSQL profileMemo = null;
		ResultSet rs = null;
		ResultSet rsMemo = null;

		try {
			profile = new ProfileSQL(agent, sqlQuery);
			profile.setString(1, refNumber);
			profile.setString(2, customerNumber);
			rs = profile.executeQuery();

			profileMemo = new ProfileSQL(agent, sqlMemoQuery);
			profileMemo.setString(1, refNumber);
			profileMemo.setString(2, customerNumber);
			rsMemo = profileMemo.executeQuery();

			while (rs.next()) {
				alertService.setCustomerNumber(rs.getString("CIFONALRT.ACN"));
				alertService.setReferenceNumber(refNumber);
				alertService.setStatusDate(rs.getDate("CIFONALRT.STATDATE"));
				alertService.setAlertStatus(rs.getString("CIFONALRT.STATUS"));

				final String number = GlobalManager.getMaskedFieldValue(agent.getUserClass(), "ACN_CID", rs.getString("CIFONALRT.CID"));
				if (number != null) {
					alertService.setAccountNumber(number);
				} else {
					alertService.setAccountNumber(rs.getString("CIFONALRT.CID"));
				}

				alertService.setCateoryId(rs.getString("CIFONALRT.ALRTCAT"));
				alertService.setAlertId(rs.getString("CIFONALRT.ALRTID"));
				alertService.setAlertType(rs.getString("UTBLALRTTYPE.ALRTTYPE"));

				if (rsMemo.next()) {
					alertService.setAlertMessage(rsMemo.getString("ALRTMSG"));
				}

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					alertService.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "getAlertHistoryDetail", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
			if (profileMemo != null) {
				profileMemo.close(rsMemo);
			}
		}
		return alertService;
	}

	/**
	 * 
	 * To get the Search Alert History from CIFONALRT Table
	 * 
	 * @param agent
	 * @param customerNumber
	 * @param fromDate
	 * @param toDate
	 * @param alertCategory
	 * @param alertStatus
	 * @return arrayList<AlertService>
	 * @throws SanchezException
	 */
	public static List<AlertService> getSearchedAlertHistoryList(final Agent agent, final String customerNumber, final Date fromDate, final Date toDate, final String alertCategory, final String alertStatus) throws SanchezException {

		final List<AlertService> alertHistoryList = new ArrayList<AlertService>();

		final StringBuilder inputBuilder = new StringBuilder("SELECT ALRTCAT, ALRTID, STATUS, STATDATE, REFNO FROM CIFONALRT WHERE ");

		final String frmDate = Utility.toProfileString(fromDate);
		final String tooDate = Utility.toProfileString(toDate);
		final String alertCategoryId = Utility.toProfileString(alertCategory);
		final String alertStatusId = Utility.toProfileString(alertStatus);
		final List<String> whereClauseList = new ArrayList<String>(4);

		if (Utility.hasText(frmDate)) {
			inputBuilder.append("STATDATE >= ? AND ");
			whereClauseList.add(frmDate);
		}

		if (Utility.hasText(tooDate)) {
			inputBuilder.append("STATDATE <= ? AND ");
			whereClauseList.add(tooDate);
		}

		if (Utility.hasText(alertCategoryId)) {
			inputBuilder.append("ALRTCAT = ? AND ");
			whereClauseList.add(alertCategoryId);
		}

		if (Utility.hasText(alertStatusId)) {
			inputBuilder.append("STATUS = ? AND ");
			whereClauseList.add(alertStatusId);
		}

		inputBuilder.append("ACN = ? ");
		inputBuilder.append("ORDER BY ALRTID DESC");

		ProfileSQL profile = null;
		ResultSet rs = null;
		int params = 0;

		try {

			profile = new ProfileSQL(agent, inputBuilder.toString());

			for (; params < whereClauseList.size(); ++params) {
				profile.setString(params + 1, whereClauseList.get(params));
			}

			profile.setString(params + 1, customerNumber);

			rs = profile.executeQuery();
			while (rs.next()) {
				final AlertService alertService = new AlertService();
				alertService.setCateoryId(rs.getString("ALRTCAT"));
				alertService.setAlertId(rs.getString("ALRTID"));
				alertService.setAlertStatus(rs.getString("STATUS"));
				alertService.setStatusDate(rs.getDate("STATDATE"));
				alertService.setReferenceNumber(rs.getString("REFNO"));
				alertHistoryList.add(alertService);

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					alertService.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "getSearchedAlertHistoryList", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return alertHistoryList;
	}

	/**
	 * To get the Account Name from ACN Table
	 * 
	 * @param agent
	 * @param alertService
	 * @throws SanchezException
	 */
	private static void populateAccountName(final Agent agent, final AlertService alertService) throws SanchezException {

		final String query = "SELECT ACCTNAME FROM ACN WHERE ACN = ? AND CID = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, alertService.getCustomerNumber());
			profile.setString(2, alertService.getAccountNumber());
			rs = profile.executeQuery();
			if (rs.next()) {
				alertService.setAccountName(rs.getString("ACCTNAME"));
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "populateAccountName", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	/**
	 * Returns the list of communication methods from CIFCOMADD Table.
	 * 
	 * @param agent
	 * @param customerNumber
	 * @return list<CommunicationMethod>
	 */
	public static List<CommunicationMethod> getCustomerDefinedDeliveryAddress(final Agent agent, final String customerNumber) throws SanchezException {

		final List<CommunicationMethod> deliveryMethodList = new ArrayList<CommunicationMethod>();
		final String sqlQuery = "SELECT CIFCOMADD.COMMETID, CIFCOMADD.COMADD FROM CIFCOMADD WHERE CIFCOMADD.ACN = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, sqlQuery);
			profile.setString(1, customerNumber);
			rs = profile.executeQuery();
			while (rs.next()) {
				final CommunicationMethod communicationMethod = new CommunicationMethod();
				communicationMethod.setCustomerNumber(customerNumber);
				communicationMethod.setCommunicationMethodID(rs.getString("CIFCOMADD.COMMETID"));
				communicationMethod.setAddress(rs.getString("CIFCOMADD.COMADD"));

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					communicationMethod.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}

				deliveryMethodList.add(communicationMethod);
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "getCustomerDefinedDeliveryAddress", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return deliveryMethodList;
	}

	/**
	 * To Add a Delivery Address into CIFCOMADD Table
	 * 
	 * @param agent
	 * @param communicationMethod
	 * @return void
	 * @throws SanchezException
	 */
	public static void addDeliveryAddress(final Agent agent, final CommunicationMethod communicationMethod) throws SanchezException {

		final String query = "INSERT INTO CIFCOMADD (ACN,COMMETID,COMADD) VALUES (?,?,?)";
		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, Utility.toProfileString(communicationMethod.getCustomerNumber()));
			profile.setString(2, Utility.toProfileString(communicationMethod.getCommunicationMethodID()));
			profile.setString(3, Utility.toProfileString(communicationMethod.getAddress()));
			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "addDeliveryAddress", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {

			if (profile != null) {
				profile.close();
			}
		}
	}

	/**
	 * To Update a Delivery Address into CIFCOMADD Table
	 * 
	 * @param agent
	 * @param communicationMethod
	 * @return void
	 * @throws SanchezException
	 */
	public static void updateDeliveryAddress(final Agent agent, final CommunicationMethod communicationMethod) throws SanchezException {

		final String query = "UPDATE CIFCOMADD SET COMADD = ? WHERE ACN = ? AND COMMETID = ?";
		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, Utility.toProfileString(communicationMethod.getAddress()));
			profile.setString(2, Utility.toProfileString(communicationMethod.getCustomerNumber()));
			profile.setString(3, Utility.toProfileString(communicationMethod.getCommunicationMethodID()));
			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "addDeliveryAddress", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {

			if (profile != null) {
				profile.close();
			}
		}
	}

	/**
	 * To delete a delivery address from CIFCOMADD Table.
	 * 
	 * @param agent
	 * @param customerNumber
	 * @param communicationId
	 * @throws SanchezException
	 */
	public static void deleteDeliveryAddress(final Agent agent, final String customerNumber, final String communicationId) throws SanchezException {

		final String deliveryAddDelete = "DELETE FROM CIFCOMADD WHERE ACN = ? AND COMMETID = ?";
		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, deliveryAddDelete);
			profile.setString(1, customerNumber);
			profile.setString(2, communicationId);
			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "deleteDeliveryAddress", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	public static List<String> getEligibleCustomers(final String customerNumber) {
		final List<String> eligibleCustomers = new ArrayList<String>();
		//Distinct is removed from the query ,because Map is filtering the data.
		final String query = "SELECT SERVICE FROM CUSTSRV WHERE ACN = ?";

		ProfileSQL psql = null;
		ResultSet rs = null;
		try {
			psql = new ProfileSQL(true, query);
			psql.setString(1, customerNumber);
			rs = psql.executeQuery();
			while (rs.next()) {
				eligibleCustomers.add(rs.getString("SERVICE"));
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "getEligibleCustomers()", e.getMessage()});
		} finally {
			if (psql != null) {
				psql.close(rs);
			}
		}
		return eligibleCustomers;
	}

	/**
	 * To get delivery methods for existing alert from table CIFALTPARDDM.
	 * 
	 * @param agent
	 * @param alertService
	 * @return List of delivery methods
	 * @throws SanchezException
	 */
	public static List<String> getDeliveryMethods(final Agent agent, final AlertService alertService) throws SanchezException {

		final String query = "SELECT CIFALTPARDDM.ALRTDDM FROM CIFALTPARDDM WHERE ACN = ? AND ALRTSEQ = ? AND ALRTID = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		final List<String> deliveryMethodList = new ArrayList<String>();
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, Utility.toProfileString(alertService.getCustomerNumber()));
			profile.setString(2, Utility.toProfileString(alertService.getAlertSequence()));
			profile.setString(3, Utility.toProfileString(alertService.getAlertId()));
			rs = profile.executeQuery();

			while (rs.next()) {
				final String methodId = rs.getString("CIFALTPARDDM.ALRTDDM");
				deliveryMethodList.add(methodId);
			}

		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "getDeliveryMethods", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return deliveryMethodList;
	}

	/**
	 * To add delivery methods for a new alert into CIFALTPARDDM Table
	 * 
	 * @param agent
	 * @param alertService
	 * @param alertSequence
	 * @return void
	 * @throws SanchezException
	 */
	public static void addDeliveryMethods(final Agent agent, final AlertService alertService, final int alertSequence) throws SanchezException {
		ProfileSQL profile = null;
		final String alertMethodUpdate = "INSERT INTO CIFALTPARDDM (ACN, ALRTID, ALRTSEQ, ALRTDDM) VALUES (?,?,?,?)";
		final String[] communicationMethod = alertService.getCommunicationMethod();
		String methodId = null;
		try {
			for (int i = 0; i <= communicationMethod.length - 1; i++) {
				methodId = communicationMethod[i];

				// Close through each for-loop iteration.
				if (profile != null) {
					profile.close();
				}

				profile = new ProfileSQL(agent, alertMethodUpdate);
				profile.setString(1, Utility.toProfileString(alertService.getCustomerNumber()));
				profile.setString(2, Utility.toProfileString(alertService.getAlertId()));
				profile.setInt(3, alertSequence);
				profile.setString(4, Utility.toProfileString(methodId));
				profile.executeUpdate();
			}

		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "addDeliveryMethods", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	/**
	 * To add delivery methods for an existing alert into CIFALTPARDDM Table
	 * 
	 * @param agent
	 * @param alertService
	 * @return void
	 * @throws SanchezException
	 */
	public static void updateDeliveryMethods(final Agent agent, final AlertService alertService) throws SanchezException {
		ProfileSQL profile = null;
		final String alertMethodUpdate = "INSERT INTO CIFALTPARDDM (ACN, ALRTID, ALRTSEQ, ALRTDDM) VALUES (?,?,?,?)";
		final String[] communicationMethod = alertService.getCommunicationMethod();
		String methodId = null;
		try {
			for (int i = 0; i <= communicationMethod.length - 1; i++) {
				methodId = communicationMethod[i];

				// Close through each for-loop iteration.
				if (profile != null) {
					profile.close();
				}

				profile = new ProfileSQL(agent, alertMethodUpdate);
				profile.setString(1, Utility.toProfileString(alertService.getCustomerNumber()));
				profile.setString(2, Utility.toProfileString(alertService.getAlertId()));
				profile.setString(3, Utility.toProfileString(alertService.getAlertSequence()));
				profile.setString(4, Utility.toProfileString(methodId));
				profile.executeUpdate();
			}

		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "updateDeliveryMethods", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	/**
	 * To delete Delivery Methods in CIFALTPARDDM Table
	 * 
	 * @param agent
	 * @param alertService
	 * @return void
	 * @throws SanchezException
	 */
	public static void deleteDeliveryMethods(final Agent agent, final AlertService alertService) throws SanchezException {
		ProfileSQL profileDelete = null;
		final String alertDelete = "DELETE FROM CIFALTPARDDM WHERE ACN = ? AND ALRTSEQ = ? AND ALRTID = ?";
		try {
			profileDelete = new ProfileSQL(agent, alertDelete);
			profileDelete.setString(1, Utility.toProfileString(alertService.getCustomerNumber()));
			profileDelete.setString(2, Utility.toProfileString(alertService.getAlertSequence()));
			profileDelete.setString(3, Utility.toProfileString(alertService.getAlertId()));
			profileDelete.executeUpdate();
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "deleteDeliveryMethods", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profileDelete != null) {
				profileDelete.close();
			}
		}
	}

	public static AlertService getAlertDetails(final Agent agent, final AlertService alertService) throws SanchezException {

		final String query = "SELECT FMDESC, ALRTADDL, ALRTCAT, ALRTDDM FROM UTBLALRTTYPE WHERE ALRTID = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, Utility.toProfileString(alertService.getAlertId()));
			rs = profile.executeQuery();

			while (rs.next()) {
				alertService.setDescription(rs.getString("FMDESC"));
				alertService.setAlertDescription(rs.getString("ALRTADDL"));
				alertService.setCateoryId(rs.getString("ALRTCAT"));
				alertService.setDefaultMethod(rs.getString("ALRTDDM"));
			}

		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "AlertServiceManager", "getAlertDetails", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return alertService;
	}
}
