package com.fisglobal.manager;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fisglobal.base.Application;
import com.fisglobal.base.Logger;
import com.fisglobal.base.SanchezException;
import com.fisglobal.bean.Account;
import com.fisglobal.bean.AccountSearchCriteria;
import com.fisglobal.bean.Address;
import com.fisglobal.bean.Agent;
import com.fisglobal.bean.Collateral;
import com.fisglobal.bean.CollateralCode;
import com.fisglobal.bean.CollateralInsurance;
import com.fisglobal.bean.DepositAccount;
import com.fisglobal.bean.LoanAccount;
import com.fisglobal.host.ProfileSQL;
import com.fisglobal.utils.Utility;

public final class CollateralManager extends BaseManager {
	private static Map<String, Map<String, CollateralCode>> collateralCodesByGroup;

	static {
		reload();
	}

	private CollateralManager() {
	}

	public static void reload() {
		collateralCodesByGroup = fillCollateralCodesByGroup();
	}

	public static Map<String, Map<String, CollateralCode>> fillCollateralCodesByGroup() {
		final List<String> uniqueRecordList = new ArrayList<String>();
		String collateralGroupQuery = "SELECT DISTINCT GRP FROM UTBLCOLCD ORDER BY GRP";

		// If data item protection is enabled ,removed the DISTINCT keyword from the query.
		if (Application.isDataItemProtection()) {
			collateralGroupQuery = "SELECT GRP FROM UTBLCOLCD ORDER BY GRP";
		}

		String productGroup = null;
		ProfileSQL profile = null;
		ProfileSQL profile2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		final String collateralQuery = "SELECT KEY,TYPE,FMDESC,GRP,COLPCT,SEC,ADR FROM UTBLCOLCD WHERE GRP = ? ORDER BY KEY";

		final Map<String, Map<String, CollateralCode>> collateralTypeMap = new HashMap<String, Map<String, CollateralCode>>();
		try {
			profile = new ProfileSQL(true, collateralGroupQuery);
			rs1 = profile.executeQuery();

			while (rs1.next()) {
				if (Application.isDataItemProtection()) {

					// Filter the duplicate records if protection is enabled.
					if (!Utility.filterRecords(rs1, collateralGroupQuery, uniqueRecordList)) {
						productGroup = rs1.getString(1);
					} else {
						productGroup = null;
					}
				} else {
					productGroup = rs1.getString(1);
				}

				if (productGroup != null) {

					if (profile2 != null) {
						profile2.close(rs2);
					}

					profile2 = new ProfileSQL(true, collateralQuery);
					profile2.setString(1, productGroup);
					rs2 = profile2.executeQuery();
					final Map<String, CollateralCode> CollateralTypes = new TreeMap<String, CollateralCode>();

					while (rs2.next()) {
						final CollateralCode collateralCode = new CollateralCode();
						collateralCode.setCode(rs2.getString("KEY"));
						collateralCode.setType(rs2.getString("TYPE"));
						collateralCode.setDescription(rs2.getString("FMDESC"));
						collateralCode.setGroup(rs2.getString("GRP"));
						collateralCode.setProvisionPercentage(rs2.getFloat("COLPCT"));
						collateralCode.setUnsecured(rs2.getBoolean("SEC"));
						collateralCode.setDefaultAddress(rs2.getBoolean("ADR"));
						CollateralTypes.put(collateralCode.getCode(), collateralCode);
					}

					collateralTypeMap.put(productGroup, CollateralTypes);
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "fillCollateralCodesByGroup()", e.getMessage()});
		} finally {
			Logger.info("Caching table: UTBLCOLCD - collateralTypeMap (" + collateralTypeMap.size() + ")");
			if (profile != null) {
				profile.close(rs1);
			}
			if (profile2 != null) {
				profile2.close(rs2);
			}
		}
		return collateralTypeMap;
	}

	public static void populateLoanCollateralDetail(final Agent agent, final LoanAccount account) {
		final String query = "SELECT RLVP,LVV,LTVC,LVVP,TOTCOL,COLL,MCOL,LTVCOPT,OLVR FROM LN,COL WHERE CID = ? AND LN.COLL=COL.COLL";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber()));
			rs = profile.executeQuery();
			if (rs.next()) {
				account.setRequiredLoantoValue(rs.getInt("RLVP"));
				account.setAllowedLoantoValueVariance(rs.getInt("LVV"));
				account.setCurrentLoantoValue(rs.getInt("LTVC"));
				account.setCurrentLoantoValueVariance(rs.getInt("LVVP"));
				account.setTotalCollateralValue(rs.getBigDecimal("TOTCOL"));
				account.setCollateral(rs.getString("COLL"));
				account.setMultipleCollateralIndicator(rs.getBoolean("MCOL"));
				account.setLoanToValueComputationOption(rs.getString("LTVCOPT"));
				account.setOriginalLoanToValueRatio(rs.getInt("OLVR"));

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					account.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "populateLoanCollateralDetail()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	public static void updateCollateralBalances(final Agent agent, final LoanAccount account) throws SanchezException {
		final String query = "UPDATE LN SET RLVP = ?, LVV = ?, LTVCOPT = ? WHERE CID = ?";
		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setInt(1, account.getRequiredLoantoValue());
			profile.setInt(2, account.getAllowedLoantoValueVariance());
			profile.setString(3, account.getLoanToValueComputationOption());
			profile.setString(4, Utility.toProfileString(account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber())));
			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "CollateralManager", "updateCollateralBalances()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	public static void populateCollaterals(final Agent agent, final LoanAccount account) {
		final List<Collateral> collaterals = new ArrayList<Collateral>(20);

		final String query = "SELECT COLL, SCOLCD, DESC FROM COL, LNCOL WHERE COL.COLL = LNCOL.COLL AND LNCOL.CID = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		final String number = account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber());
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, number);
			rs = profile.executeQuery();

			while (rs.next()) {
				final Collateral collateral = new Collateral();
				final CollateralCode code = collateralCodesByGroup.get(account.getGroup()).get(rs.getString("SCOLCD"));
				collateral.setCode(code);
				collateral.setId(rs.getString("COLL"));
				collateral.setDesc(rs.getString("DESC"));
				collaterals.add(collateral);

				// Set COLATTRIB for each column if protection is enabled.
				if (Application.isDataItemProtection()) {
					collateral.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}

			if (profile != null) {
				profile.close(rs);
			}

			// Check for unsecured collateral (which only exists if no other collateral is linked to the account)
			if (Utility.isEmpty(collaterals)) {
				profile = new ProfileSQL(agent, "SELECT COLL, COLCD FROM LN WHERE CID=?");
				profile.setString(1, number);
				rs = profile.executeQuery();

				if (rs.next()) {
					final Collateral collateral = new Collateral(rs.getString("COLL"));
					if (rs.getString("COLCD") != null) {
						final CollateralCode code = collateralCodesByGroup.get(account.getGroup()).get(rs.getString("COLCD"));
						if (code != null && code.isUnsecured()) {
							collateral.setCode(code);

							// Set COLATTRIB for each column if protection is enabled.
							if (Application.isDataItemProtection()) {
								collateral.setProtectionRow(Utility.getTableColumnNamesList(rs));
							}
							collaterals.add(collateral);
						}
					}
				}
			}

			account.setCollaterals(collaterals);
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "populateCollaterals()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	public static void deleteCollateral(final Agent agent, final LoanAccount account, final Collateral collateral) throws SanchezException {
		if (collateral.getId() == null) {
			return;
		}

		final String query = "DELETE FROM LNCOL WHERE CID = ? AND COLL = ?";
		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber()));
			profile.setString(2, Utility.toProfileString(collateral.getId()));
			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "deleteCollateral()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	public static void deleteAccountCollateral(final Agent agent, final LoanAccount account, final Collateral collateral) throws SanchezException {
		final String query = "UPDATE LN SET COLL = NULL, COLCD = NULL WHERE CID = ?";
		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber()));
			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "CollateralManager", "deleteCollateral()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	public static boolean isExistingCollateralId(final Agent agent, final LoanAccount account, final String collateralId) throws SanchezException {
		final String query = "SELECT COLL FROM LNCOL WHERE CID = ? AND COLL = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber()));
			profile.setString(2, Utility.toProfileString(collateralId));
			rs = profile.executeQuery();
			if (rs.next()) {
				flag = true;
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "isExistingCollateralId()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return flag;
	}

	public static void addCollateral(final Agent agent, final Collateral collateral, final LoanAccount account) throws SanchezException {
		final String collateralId = generateCollateralId(agent, account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber()));
		final String query = "INSERT INTO COL (CNAM, OWNER, CUSIP, NSH, PVAL, POF, AOF, COLVAL, COLPCT, CRCD, REVFRE, COLRVND, COLRVLD, AVRSL, PLDGCB, OMDT, AROD, COLACN, ACTY, COLDES, AD1, AD2, CITY, STATE, MZIP, CNTRY, CENTR, LOT, WARD, PARC, SMSA, APDT, APVAL, PRICE, MAKE, MODEL, ID, CLYR, CLTYP, TITLE, TNUM, COLDES2, SCOLCD, PROPCD, LEGAL1, PROPREGNUM, ORGNOSH, OCOLVAL, TAXROLLNUM, ORGBLDAPPVAL, ORGLANDAPVAL, CURBLDAPPVAL, CURLANDAPVAL, PROPAGE, PROPSIZE, PUOM, NUNIT, HOMESTYL, OWNOCC, PROPREGEXPDT, POSSESDT, ORGAPPRDT, COLL, CONTRACT, CDT, BPDT, BLDLOT, SURVEYDT, SOLDDT, PNAME, TCONAME, TCOCONT, TCOPHONE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		final String LNCOLquery = "INSERT INTO LNCOL (FPLDG, PCTPLDG, PCTPLDGCOPT, MAXPLDG, CID, COLL) VALUES (?,?,?,?,?,?)";
		final String accountQuery = "UPDATE LN SET COLCD=? WHERE CID = ? AND COLL IS NULL AND COLCD IS NULL";
		ProfileSQL profile = null;
		try {
			TransactionManager.beginTransaction(agent, true);
			profile = new ProfileSQL(agent, query);

			// Store only Secure Collateral.
			if (!collateral.getCode().isUnsecured()) {
				profile.setString(1, Utility.toProfileString(collateral.getCollateralName()));
				profile.setString(2, Utility.toProfileString(collateral.getOwner()));
				profile.setString(3, Utility.toProfileString(collateral.getCusipNumber()));
				profile.setString(4, Utility.toProfileString(collateral.getNumberOfShares()));
				profile.setString(5, collateral.getParValue() == null ? null : String.valueOf(collateral.getParValue()));
				profile.setBoolean(6, collateral.isPowerOnFile());
				profile.setBoolean(7, collateral.isAssignmentOnFile());
				profile.setBigDecimal(8, collateral.getCurrentValue());
				profile.setString(9, collateral.getProvisionPercentage() == null ? null : String.valueOf(collateral.getProvisionPercentage()));
				profile.setString(10, Utility.toProfileString(collateral.getCurrencyCode()));
				profile.setString(11, Utility.toProfileString(collateral.getReviewFrequency()));
				profile.setString(12, Utility.toProfileDateString(collateral.getReviewNextDate()));
				profile.setString(13, Utility.toProfileDateString(collateral.getReviewLastDate()));
				profile.setBoolean(14, collateral.isMayBeReleased());
				profile.setString(15, Utility.toProfileString(collateral.getPledgedBalanceOption()));
				profile.setBoolean(16, collateral.isOverrideMaturityDate());
				profile.setString(17, Utility.toProfileString(collateral.getAutoReleaseOffsetDays()));
				profile.setString(18, collateral.getNumber());
				profile.setString(19, Utility.toProfileString(collateral.getAccountType()));
				profile.setString(20, Utility.toProfileString(collateral.getDescription()));
				profile.setString(21, Utility.toProfileString(collateral.getAddress().getAddressLine1()));
				profile.setString(22, Utility.toProfileString(collateral.getAddress().getAddressLine2()));
				profile.setString(23, Utility.toProfileString(collateral.getAddress().getCity()));
				profile.setString(24, Utility.toProfileString(collateral.getAddress().getState()));
				profile.setString(25, Utility.toProfileString(collateral.getAddress().getZipCode()));
				profile.setString(26, Utility.toProfileString(collateral.getAddress().getCountry()));
				profile.setString(27, Utility.toProfileString(collateral.getCensusTract()));
				profile.setString(28, Utility.toProfileString(collateral.getLotBlock()));
				profile.setString(29, Utility.toProfileString(collateral.getWard()));
				profile.setString(30, Utility.toProfileString(collateral.getParcel()));
				profile.setString(31, Utility.toProfileString(collateral.getSmsaCode()));
				profile.setString(32, Utility.toProfileDateString(collateral.getAppraisalDate()));
				profile.setString(33, collateral.getAppraisedValue() == null ? null : String.valueOf(collateral.getAppraisedValue()));
				profile.setString(34, collateral.getSalesPrice() == null ? null : String.valueOf(collateral.getSalesPrice()));
				profile.setString(35, Utility.toProfileString(collateral.getMake()));
				profile.setString(36, Utility.toProfileString(collateral.getModel()));
				profile.setString(37, Utility.toProfileString(collateral.getIdentificationNumber()));
				profile.setString(38, Utility.toProfileString(collateral.getYearOfManufacture()));
				profile.setString(39, Utility.toProfileString(collateral.getBodyStyle()));
				profile.setBoolean(40, collateral.isTitleOnFileIndicator());
				profile.setString(41, Utility.toProfileString(collateral.getTitleNumber()));
				profile.setString(42, Utility.toProfileString(collateral.getDescription2()));
				profile.setString(43, Utility.toProfileString(collateral.getCode().getCode()));
				profile.setString(44, Utility.toProfileString(collateral.getPropertyCode()));
				profile.setString(45, Utility.toProfileString(collateral.getLegalDescription()));
				profile.setString(46, Utility.toProfileString(collateral.getRegistryNumber()));
				profile.setString(47, Utility.toProfileString(collateral.getNumberOfShares()));
				profile.setBigDecimal(48, collateral.getCurrentValue());
				profile.setString(49, Utility.toProfileString(collateral.getTaxRollNumber()));
				profile.setString(50, collateral.getCurrentBuildingAppraisalValue() == null ? null : String.valueOf(collateral.getCurrentBuildingAppraisalValue()));
				profile.setString(51, collateral.getCurrentLandAppraisalValue() == null ? null : String.valueOf(collateral.getCurrentLandAppraisalValue()));
				profile.setString(52, collateral.getCurrentBuildingAppraisalValue() == null ? null : String.valueOf(collateral.getCurrentBuildingAppraisalValue()));
				profile.setString(53, collateral.getCurrentLandAppraisalValue() == null ? null : String.valueOf(collateral.getCurrentLandAppraisalValue()));
				profile.setString(54, Utility.toProfileString(collateral.getPropertyAge()));
				profile.setString(55, Utility.toProfileString(collateral.getPropertySize()));
				profile.setString(56, Utility.toProfileString(collateral.getPropertySizeUnitOfMeasurement()));
				profile.setString(57, Utility.toProfileString(collateral.getNumberOfUnits()));
				profile.setString(58, Utility.toProfileString(collateral.getStyleOfHome()));
				profile.setBoolean(59, collateral.isOwnerOccupied());
				profile.setString(60, Utility.toProfileDateString(collateral.getPropertyRegistrationExpirationDate()));
				profile.setString(61, Utility.toProfileDateString(collateral.getPossessionDate()));
				profile.setString(62, Utility.toProfileDateString(collateral.getAppraisalDate()));
				profile.setString(63, Utility.toProfileString(collateralId));
				profile.setBoolean(64, collateral.isContractCode());
				profile.setString(65, Utility.toProfileDateString(collateral.getContractDate()));
				profile.setString(66, Utility.toProfileDateString(collateral.getBuildingPermitDate()));
				profile.setString(67, Utility.toProfileString(collateral.getBuildingLotNumber()));
				profile.setString(68, Utility.toProfileDateString(collateral.getDateOfSurvey()));
				profile.setString(69, Utility.toProfileDateString(collateral.getDateSold()));
				profile.setString(70, Utility.toProfileString(collateral.getPurchaserName()));
				profile.setString(71, Utility.toProfileString(collateral.getCompanyTitleName()));
				profile.setString(72, Utility.toProfileString(collateral.getCompanyTitleContact()));
				profile.setString(73, Utility.toProfileString(collateral.getCompanyTitlePhone()));
				profile.executeUpdate();

				if (profile != null) {
					profile.close();
				}

				profile = new ProfileSQL(agent, LNCOLquery);
				profile.setBigDecimal(1, collateral.getFixedPledgedAmount());
				profile.setFloat(2, collateral.getPercentPledged());
				profile.setBoolean(3, collateral.isPercentPledgedCollateralOption());
				profile.setBigDecimal(4, collateral.getMaximumPledgeAmount());
				profile.setString(5, Utility.toProfileString(account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber())));
				profile.setString(6, Utility.toProfileString(collateralId));
				profile.executeUpdate();

				if (profile != null) {
					profile.close();
				}
			}

			profile = new ProfileSQL(agent, accountQuery);
			profile.setString(1, collateral.getCode().getCode());
			profile.setString(2, account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber()));
			profile.executeUpdate();

			try {
				TransactionManager.commit(agent);
			} catch (final SanchezException el) {
				TransactionManager.rollback(agent);
				Logger.warn("exception", new String[] {el.getClass().getName(), "CollateralManager", "addCollateral()", el.getMessage()});
				throw el;
			}

		} catch (final SQLException e) {
			TransactionManager.rollback(agent);
			Logger.warn("exception", new String[] {e.getClass().getName(), "CollateralManager", "addCollateral()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			TransactionManager.endTransaction(agent);
			if (profile != null) {
				profile.close();
			}
		}
	}

	public static void populateCollateralDetail(final Agent agent, final Collateral collateral, final LoanAccount account) {
		final String query = "SELECT ACTY, AD1, AD2, CITY, STATE, MZIP, CNTRY, APDT, APVAL, AOF, AROD, CLTYP, CLBL, CENTR, CNAM, CRCD, COLVAL, CUSIP, DTD, DESC, COLDES, COLDES2, DUED, LNCOL.FPLDG, COL.COLL, ID, LEGAL1, LOT, MAKE, LNCOL.MAXPLDG, AVRSL, MODEL, MLN, COLACN, NSH, OMDT, OWNER, PARC, PVAL, LNCOL.PCTPLDG, LNCOL.PLDGAMT, PLDGCB, LNCOL.PCTPLDGCOPT, POF, PROPCD, COLPCT, REVFRE, COLRVLD, COLRVND, PRICE, SMSA, TITLE, TNUM, CLYR, WARD, PROPREGNUM, ORGNOSH, OCOLVAL, TAXROLLNUM, ORGBLDAPPVAL, ORGLANDAPVAL, CURBLDAPPVAL, CURLANDAPVAL, PROPAGE, PROPSIZE, PUOM, NUNIT, HOMESTYL, OWNOCC, PROPREGEXPDT, POSSESDT, PSRLNBAL, FMB, FPLENSTADDR1, FPLENSTADDR2, FPLENCITY, FPLENPROV, FPLENCNTRY, FPLENPCD, FPLENREFNUM, FPLENPHNUM, FPLENREP, SSRLNBAL, SPLENNAME, SPLENSTADDR1, SPLENSTADDR2, SPLENCITY, SPLENPROV, SPLENCNTRY, SPLENPCD, SPLENREFNUM, SPLENPHNUM, SPLENREP, ORGAPPRDT, PLDGCTOT, CONTRACT, CDT, BPDT, BLDLOT, SURVEYDT, SOLDDT, PNAME, TCONAME, TCOCONT, TCOPHONE FROM COL, LNCOL WHERE COL.COLL = LNCOL.COLL AND  LNCOL.CID=? AND COL.COLL=?";
		final String pledgedAccountsQuery = "SELECT CID, LNCOL.PLDGAMT FROM LNCOL WHERE COLL = ? ORDER BY CID";
		ProfileSQL profile1 = null;
		ProfileSQL profile2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;

		try {
			profile1 = new ProfileSQL(agent, query);
			profile1.setString(1, account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber()));
			profile1.setString(2, collateral.getId());
			rs1 = profile1.executeQuery();
			if (rs1.next()) {
				collateral.setAccountType(rs1.getString("ACTY"));
				collateral.getAddress().setAddressLine1(rs1.getString("AD1"));
				collateral.getAddress().setAddressLine2(rs1.getString("AD2"));
				collateral.getAddress().setCity(rs1.getString("CITY"));
				collateral.getAddress().setState(rs1.getString("STATE"));
				collateral.getAddress().setZipCode(rs1.getString("MZIP"));
				collateral.getAddress().setCountry(rs1.getString("CNTRY"));
				collateral.setAppraisalDate(rs1.getDate("APDT"));
				collateral.setAppraisedValue(rs1.getBigDecimal("APVAL"));
				collateral.setAssignmentOnFile(rs1.getBoolean("AOF"));
				collateral.setAutoReleaseOffsetDays(rs1.getInt("AROD"));
				collateral.setBodyStyle(rs1.getString("CLTYP"));
				collateral.setCallable(rs1.getBoolean("CLBL"));
				collateral.setCensusTract(rs1.getString("CENTR"));
				collateral.setCollateralName(rs1.getString("CNAM"));
				collateral.setCurrencyCode(rs1.getString("CRCD"));
				collateral.setCurrentValue(rs1.getBigDecimal("COLVAL"));
				collateral.setCusipNumber(rs1.getString("CUSIP"));
				collateral.setDated(rs1.getDate("DTD"));
				collateral.setDesc(rs1.getString("DESC"));
				collateral.setDescription(rs1.getString("COLDES"));
				collateral.setDescription2(rs1.getString("COLDES2"));
				collateral.setDueDate(rs1.getDate("DUED"));
				collateral.setFixedPledgedAmount(rs1.getBigDecimal("LNCOL.FPLDG"));
				collateral.setId(rs1.getString("COLL"));
				collateral.setIdentificationNumber(rs1.getString("ID"));
				collateral.setLegalDescription(rs1.getString("LEGAL1"));
				collateral.setLotBlock(rs1.getString("LOT"));
				collateral.setMake(rs1.getString("MAKE"));
				collateral.setMaximumPledgeAmount(rs1.getBigDecimal("LNCOL.MAXPLDG"));
				collateral.setMayBeReleased(rs1.getBoolean("AVRSL"));
				collateral.setModel(rs1.getString("MODEL"));
				collateral.setMultipleLoanIndicator(rs1.getBoolean("MLN"));
				collateral.setNumber(rs1.getString("COLACN"));
				collateral.setNumberOfShares(rs1.getBigDecimal("NSH") == null ? null : rs1.getBigDecimal("NSH").intValue());
				collateral.setOverrideMaturityDate(rs1.getBoolean("OMDT"));
				collateral.setOwner(rs1.getString("OWNER"));
				collateral.setParcel(rs1.getString("PARC"));
				collateral.setParValue(rs1.getBigDecimal("PVAL"));
				collateral.setPercentPledged(rs1.getFloat("LNCOL.PCTPLDG"));
				collateral.setPledgedAmount(rs1.getBigDecimal("LNCOL.PLDGAMT"));
				collateral.setPledgedBalanceOption(rs1.getBigDecimal("PLDGCB") == null ? null : rs1.getBigDecimal("PLDGCB").intValue());
				collateral.setPercentPledgedCollateralOption(rs1.getBoolean("LNCOL.PCTPLDGCOPT"));
				collateral.setPowerOnFile(rs1.getBoolean("POF"));
				collateral.setPropertyCode(rs1.getString("PROPCD"));
				collateral.setProvisionPercentage(rs1.getBigDecimal("COLPCT") == null ? null : rs1.getBigDecimal("COLPCT").floatValue());
				collateral.setReviewFrequency(rs1.getString("REVFRE"));
				collateral.setReviewLastDate(rs1.getDate("COLRVLD"));
				collateral.setReviewNextDate(rs1.getDate("COLRVND"));
				collateral.setSalesPrice(rs1.getBigDecimal("PRICE"));
				collateral.setSmsaCode(rs1.getBigDecimal("SMSA") == null ? null : rs1.getBigDecimal("SMSA").intValue());
				collateral.setTitleOnFileIndicator(rs1.getBoolean("TITLE"));
				collateral.setTitleNumber(rs1.getString("TNUM"));
				collateral.setYearOfManufacture(rs1.getString("CLYR"));
				collateral.setWard(rs1.getBigDecimal("WARD") == null ? null : rs1.getBigDecimal("WARD").intValue());
				collateral.setRegistryNumber(rs1.getString("PROPREGNUM"));
				collateral.setOriginalNumberOfShares(rs1.getInt("ORGNOSH"));
				collateral.setOriginalValue(rs1.getBigDecimal("OCOLVAL"));
				collateral.setTaxRollNumber(rs1.getString("TAXROLLNUM"));
				collateral.setOriginalBuildingAppraisalValue(rs1.getBigDecimal("ORGBLDAPPVAL"));
				collateral.setOriginalLandAppraisalValue(rs1.getBigDecimal("ORGLANDAPVAL"));
				collateral.setCurrentBuildingAppraisalValue(rs1.getBigDecimal("CURBLDAPPVAL"));
				collateral.setCurrentLandAppraisalValue(rs1.getBigDecimal("CURLANDAPVAL"));
				collateral.setPropertyAge(rs1.getBigDecimal("PROPAGE") == null ? null : rs1.getBigDecimal("PROPAGE").intValue());
				collateral.setPropertySize(rs1.getBigDecimal("PROPSIZE") == null ? null : rs1.getBigDecimal("PROPSIZE").intValue());
				collateral.setPropertySizeUnitOfMeasurement(rs1.getBigDecimal("PUOM") == null ? null : rs1.getBigDecimal("PUOM").intValue());
				collateral.setNumberOfUnits(rs1.getBigDecimal("NUNIT") == null ? null : rs1.getBigDecimal("NUNIT").intValue());
				collateral.setStyleOfHome(rs1.getBigDecimal("HOMESTYL") == null ? null : rs1.getBigDecimal("HOMESTYL").intValue());
				collateral.setOwnerOccupied(rs1.getBoolean("OWNOCC"));
				collateral.setPropertyRegistrationExpirationDate(rs1.getDate("PROPREGEXPDT"));
				collateral.setPossessionDate(rs1.getDate("POSSESDT"));
				collateral.setPrimaryLienBalance(rs1.getBigDecimal("PSRLNBAL"));
				collateral.setFirstPositionLenderName(rs1.getString("FMB"));
				collateral.getFirstPositionLenderAddress().setAddressLine1(rs1.getString("FPLENSTADDR1"));
				collateral.getFirstPositionLenderAddress().setAddressLine2(rs1.getString("FPLENSTADDR2"));
				collateral.getFirstPositionLenderAddress().setCity(rs1.getString("FPLENCITY"));
				collateral.getFirstPositionLenderAddress().setState(rs1.getString("FPLENPROV"));
				collateral.getFirstPositionLenderAddress().setZipCode(rs1.getString("FPLENPCD"));
				collateral.getFirstPositionLenderAddress().setCountry(rs1.getString("FPLENCNTRY"));
				collateral.setFirstPositionLenderReferenceNumber(rs1.getString("FPLENREFNUM"));
				collateral.setFirstPositionLenderPhoneNumber(rs1.getString("FPLENPHNUM"));
				collateral.setFirstPositionLenderRepresentative(rs1.getString("FPLENREP"));
				collateral.setSecondaryLienBalance(rs1.getBigDecimal("SSRLNBAL"));
				collateral.setSecondPositionLenderName(rs1.getString("SPLENNAME"));
				collateral.getSecondPositionLenderAddress().setAddressLine1(rs1.getString("SPLENSTADDR1"));
				collateral.getSecondPositionLenderAddress().setAddressLine2(rs1.getString("SPLENSTADDR2"));
				collateral.getSecondPositionLenderAddress().setCity(rs1.getString("SPLENCITY"));
				collateral.getSecondPositionLenderAddress().setState(rs1.getString("SPLENPROV"));
				collateral.getSecondPositionLenderAddress().setZipCode(rs1.getString("SPLENPCD"));
				collateral.getSecondPositionLenderAddress().setCountry(rs1.getString("SPLENCNTRY"));
				collateral.setSecondPositionLenderReferenceNumber(rs1.getString("SPLENREFNUM"));
				collateral.setSecondPositionLenderPhoneNumber(rs1.getString("SPLENPHNUM"));
				collateral.setSecondPositionLenderRepresentative(rs1.getString("SPLENREP"));
				collateral.setOriginalAppraisalDate(rs1.getDate("ORGAPPRDT"));
				collateral.setPledgedCollateralTotal(rs1.getBigDecimal("PLDGCTOT"));
				collateral.setContractCode(rs1.getBoolean("CONTRACT"));
				collateral.setContractDate(rs1.getDate("CDT"));
				collateral.setBuildingPermitDate(rs1.getDate("BPDT"));
				collateral.setBuildingLotNumber(rs1.getString("BLDLOT"));
				collateral.setDateOfSurvey(rs1.getDate("SURVEYDT"));
				collateral.setDateSold(rs1.getDate("SOLDDT"));
				collateral.setPurchaserName(rs1.getString("PNAME"));
				collateral.setCompanyTitleName(rs1.getString("TCONAME"));
				collateral.setCompanyTitleContact(rs1.getString("TCOCONT"));
				collateral.setCompanyTitlePhone(rs1.getString("TCOPHONE"));

				profile2 = new ProfileSQL(agent, pledgedAccountsQuery);
				profile2.setString(1, collateral.getId());
				rs2 = profile2.executeQuery();
				final HashMap<String, BigDecimal> pledgedLoanAccounts = new HashMap<String, BigDecimal>();
				while (rs2.next()) {
					final String accountNumber = rs2.getString("CID");
					final BigDecimal pledgedAmount = rs2.getBigDecimal("LNCOL.PLDGAMT");
					pledgedLoanAccounts.put(accountNumber, pledgedAmount);

					// Set COLATTRIB for each column if protection is enabled.  
					if (Application.isDataItemProtection()) {
						if (collateral.getProtectionRow() != null) {
							collateral.getProtectionRow().putAll(Utility.getTableColumnNamesList(rs2));
						} else {
							collateral.setProtectionRow(Utility.getTableColumnNamesList(rs2));
						}
					}
				}

				collateral.setPledgedLoanAccounts(pledgedLoanAccounts);

				// Set COLATTRIB for each column if protection is enabled.  
				if (Application.isDataItemProtection()) {
					if (collateral.getProtectionRow() != null) {
						collateral.getProtectionRow().putAll(Utility.getTableColumnNamesList(rs2));
					} else {
						collateral.setProtectionRow(Utility.getTableColumnNamesList(rs2));
					}
				}
			}

		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "populateCollateralDetail()", e.getMessage()});
		} finally {
			if (profile1 != null) {
				profile1.close(rs1);
			}
			if (profile2 != null) {
				profile2.close(rs2);
			}
		}

	}

	public static void updateCollateral(final Agent agent, final Collateral collateral, final LoanAccount account) throws SanchezException {
		final String query = "UPDATE COL SET CNAM = ?, OWNER = ?, CUSIP = ?, NSH = ?, PVAL = ?, POF = ?, AOF = ?, COLVAL = ?, COLPCT = ?, CRCD = ?, REVFRE = ?, COLRVND = ?, COLRVLD = ?, AVRSL = ?, PLDGCB = ?, OMDT = ?, AROD = ?, COLACN = ?, ACTY = ?, COLDES = ?, AD1 = ?, AD2 = ?, CITY = ?, STATE = ?, MZIP = ?, CNTRY = ?, CENTR = ?, LOT = ?, WARD = ?,	PARC = ?, SMSA = ?, APDT = ?, APVAL = ?, PRICE = ?, MAKE = ?, MODEL = ?, ID = ?, CLYR = ?, CLTYP = ?, TITLE = ?, TNUM = ?, COLDES2 = ?, SCOLCD = ?,	PROPCD = ?, LEGAL1 = ?, PROPREGNUM = ?, TAXROLLNUM = ?, CURBLDAPPVAL = ?, CURLANDAPVAL = ?, PROPAGE = ?, PROPSIZE = ?, PUOM = ?, NUNIT = ?, HOMESTYL = ?, OWNOCC = ?, PROPREGEXPDT = ?, POSSESDT = ?, CONTRACT=?, CDT=?, BPDT=?, BLDLOT=?, SURVEYDT=?, SOLDDT=?, PNAME=?, TCONAME=?, TCOCONT=?, TCOPHONE=? WHERE COLL = ?";
		final String queryLNCOL = "UPDATE LNCOL SET FPLDG = ?, PCTPLDG = ?, PCTPLDGCOPT = ?, MAXPLDG = ? WHERE COLL = ? AND CID = ? ";

		ProfileSQL profile = null;
		try {
			TransactionManager.beginTransaction(agent, true);
			profile = new ProfileSQL(agent, query);
			profile.setString(1, Utility.toProfileString(collateral.getCollateralName()));
			profile.setString(2, Utility.toProfileString(collateral.getOwner()));
			profile.setString(3, Utility.toProfileString(collateral.getCusipNumber()));
			profile.setString(4, Utility.toProfileString(collateral.getNumberOfShares()));
			profile.setString(5, collateral.getParValue() == null ? null : String.valueOf(collateral.getParValue()));
			profile.setBoolean(6, collateral.isPowerOnFile());
			profile.setBoolean(7, collateral.isAssignmentOnFile());
			profile.setBigDecimal(8, collateral.getCurrentValue());
			profile.setString(9, collateral.getProvisionPercentage() == null ? null : String.valueOf(collateral.getProvisionPercentage()));
			profile.setString(10, Utility.toProfileString(collateral.getCurrencyCode()));
			profile.setString(11, Utility.toProfileString(collateral.getReviewFrequency()));
			profile.setString(12, Utility.toProfileDateString(collateral.getReviewNextDate()));
			profile.setString(13, Utility.toProfileDateString(collateral.getReviewLastDate()));
			profile.setBoolean(14, collateral.isMayBeReleased());
			profile.setString(15, Utility.toProfileString(collateral.getPledgedBalanceOption()));
			profile.setBoolean(16, collateral.isOverrideMaturityDate());
			profile.setString(17, Utility.toProfileString(collateral.getAutoReleaseOffsetDays()));
			profile.setString(18, collateral.getNumber());
			profile.setString(19, Utility.toProfileString(collateral.getAccountType()));
			profile.setString(20, Utility.toProfileString(collateral.getDescription()));
			profile.setString(21, Utility.toProfileString(collateral.getAddress().getAddressLine1()));
			profile.setString(22, Utility.toProfileString(collateral.getAddress().getAddressLine2()));
			profile.setString(23, Utility.toProfileString(collateral.getAddress().getCity()));
			profile.setString(24, Utility.toProfileString(collateral.getAddress().getState()));
			profile.setString(25, Utility.toProfileString(collateral.getAddress().getZipCode()));
			profile.setString(26, Utility.toProfileString(collateral.getAddress().getCountry()));
			profile.setString(27, Utility.toProfileString(collateral.getCensusTract()));
			profile.setString(28, Utility.toProfileString(collateral.getLotBlock()));
			profile.setString(29, Utility.toProfileString(collateral.getWard()));
			profile.setString(30, Utility.toProfileString(collateral.getParcel()));
			profile.setString(31, Utility.toProfileString(collateral.getSmsaCode()));
			profile.setString(32, Utility.toProfileDateString(collateral.getAppraisalDate()));
			profile.setString(33, collateral.getAppraisedValue() == null ? null : String.valueOf(collateral.getAppraisedValue()));
			profile.setString(34, collateral.getSalesPrice() == null ? null : String.valueOf(collateral.getSalesPrice()));
			profile.setString(35, Utility.toProfileString(collateral.getMake()));
			profile.setString(36, Utility.toProfileString(collateral.getModel()));
			profile.setString(37, Utility.toProfileString(collateral.getIdentificationNumber()));
			profile.setString(38, Utility.toProfileString(collateral.getYearOfManufacture()));
			profile.setString(39, Utility.toProfileString(collateral.getBodyStyle()));
			profile.setBoolean(40, collateral.isTitleOnFileIndicator());
			profile.setString(41, Utility.toProfileString(collateral.getTitleNumber()));
			profile.setString(42, Utility.toProfileString(collateral.getDescription2()));
			profile.setString(43, Utility.toProfileString(collateral.getCode().getCode()));
			profile.setString(44, Utility.toProfileString(collateral.getPropertyCode()));
			profile.setString(45, Utility.toProfileString(collateral.getLegalDescription()));
			profile.setString(46, Utility.toProfileString(collateral.getRegistryNumber()));
			profile.setString(47, Utility.toProfileString(collateral.getTaxRollNumber()));
			profile.setString(48, collateral.getCurrentBuildingAppraisalValue() == null ? null : String.valueOf(collateral.getCurrentBuildingAppraisalValue()));
			profile.setString(49, collateral.getCurrentLandAppraisalValue() == null ? null : String.valueOf(collateral.getCurrentLandAppraisalValue()));
			profile.setString(50, Utility.toProfileString(collateral.getPropertyAge()));
			profile.setString(51, Utility.toProfileString(collateral.getPropertySize()));
			profile.setString(52, Utility.toProfileString(collateral.getPropertySizeUnitOfMeasurement()));
			profile.setString(53, Utility.toProfileString(collateral.getNumberOfUnits()));
			profile.setString(54, Utility.toProfileString(collateral.getStyleOfHome()));
			profile.setBoolean(55, collateral.isOwnerOccupied());
			profile.setString(56, Utility.toProfileDateString(collateral.getPropertyRegistrationExpirationDate()));
			profile.setString(57, Utility.toProfileDateString(collateral.getPossessionDate()));
			profile.setBoolean(58, collateral.isContractCode());
			profile.setString(59, Utility.toProfileDateString(collateral.getContractDate()));
			profile.setString(60, Utility.toProfileDateString(collateral.getBuildingPermitDate()));
			profile.setString(61, Utility.toProfileString(collateral.getBuildingLotNumber()));
			profile.setString(62, Utility.toProfileDateString(collateral.getDateOfSurvey()));
			profile.setString(63, Utility.toProfileDateString(collateral.getDateSold()));
			profile.setString(64, Utility.toProfileString(collateral.getPurchaserName()));
			profile.setString(65, Utility.toProfileString(collateral.getCompanyTitleName()));
			profile.setString(66, Utility.toProfileString(collateral.getCompanyTitleContact()));
			profile.setString(67, Utility.toProfileString(collateral.getCompanyTitlePhone()));
			profile.setString(68, Utility.toProfileString(collateral.getId()));
			profile.executeUpdate();

			if (profile != null) {
				profile.close();
			}
			profile = null;

			profile = new ProfileSQL(agent, queryLNCOL);
			profile.setBigDecimal(1, "fixed".equals(collateral.getPledgeType()) ? collateral.getFixedPledgedAmount() : null);
			profile.setFloat(2, "percent".equals(collateral.getPledgeType()) ? collateral.getPercentPledged() : null);
			profile.setBoolean(3, collateral.isPercentPledgedCollateralOption());
			profile.setBigDecimal(4, collateral.getMaximumPledgeAmount());
			profile.setString(5, Utility.toProfileString(collateral.getId()));
			profile.setString(6, Utility.toProfileString(account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber())));
			profile.executeUpdate();

			try {
				TransactionManager.commit(agent);
			} catch (final SanchezException el) {
				TransactionManager.rollback(agent);
				Logger.warn("exception", new String[] {el.getClass().getName(), "CollateralManager", "updateCollateral()", el.getMessage()});
				throw el;
			}

		} catch (final SQLException e) {
			TransactionManager.rollback(agent);
			Logger.warn("exception", new String[] {e.getClass().getName(), "CollateralManager", "updateCollateral()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			TransactionManager.endTransaction(agent);
			if (profile != null) {
				profile.close();
			}
		}

	}

	/**
	 * Loop through the current collateral links on the account. If the collateral link on the
	 * account is not in the selected collateral links, then delete it.
	 * 
	 * @param agent
	 * @param loanAccount
	 * @param selectedCollateralIds
	 */
	public static void deleteCollateralLinks(final Agent agent, final LoanAccount loanAccount, final String[] selectedCollateralIds) {
		for (final Collateral collateral : Utility.protectNull(loanAccount.getCollaterals())) {
			boolean delete = true;

			if (!Utility.isEmpty(selectedCollateralIds)) {
				for (final String collateralId : selectedCollateralIds) {
					if (Utility.hasText(collateralId) && collateral.getId().equals(collateralId)) {
						delete = false;
						break;
					}
				}
			}

			if (delete) {
				deleteCollateral(agent, loanAccount, new Collateral(collateral.getId()));
			}
		}
	}

	public static void insertCollateralLinks(final Agent agent, final LoanAccount loanAccount, final String[] selectedCollateralIds) throws SQLException {
		if (!Utility.isEmpty(selectedCollateralIds)) {
			for (final String collateralId : selectedCollateralIds) {
				boolean insert = true;

				if (Utility.hasText(collateralId)) {
					for (final Collateral collateral : Utility.protectNull(loanAccount.getCollaterals())) {
						if (collateral.getId().equals(collateralId)) {
							insert = false;
							break;
						}
					}
				} else {
					insert = false;
				}

				if (insert) {
					addCollateralLink(agent, loanAccount, new Collateral(collateralId));
				}
			}
		}
	}

	public static void addCollateralLink(final Agent agent, final LoanAccount loanAccount, final Collateral collateral) throws SQLException {
		final String query = "INSERT INTO LNCOL (CID, COLL) VALUES (?,?) ";
		ProfileSQL profile = null;

		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, loanAccount.getNumberMap().get(loanAccount.getNumber()) == null ? loanAccount.getNumber() : loanAccount.getNumberMap().get(loanAccount.getNumber()));
			profile.setString(2, Utility.toProfileString(collateral.getId()));
			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "addCollateralLink", e.getMessage()});
			throw e;
		}
	}

	public static void updateCollateralLinks(final Agent agent, final LoanAccount loanAccount, final String[] selectedCollateralIds) throws SanchezException {
		try {
			TransactionManager.beginTransaction(agent, true);

			deleteCollateralLinks(agent, loanAccount, selectedCollateralIds);
			insertCollateralLinks(agent, loanAccount, selectedCollateralIds);

			TransactionManager.commit(agent);

		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "updateCollateralLinks", e.getMessage()});
			TransactionManager.rollback(agent);
			throw ProfileSQL.toSanchezException(e);
		} finally {
			TransactionManager.endTransaction(agent);
		}

	}

	public static List<Account> findCollateralEligibleAccounts(final Agent agent, final AccountSearchCriteria criteria) throws SanchezException {
		final List<Account> accounts = new ArrayList<Account>();

		// Force default settings
		criteria.setAccountClass(AccountSearchCriteria.ACCOUNT_CLASS_DEPOSIT);
		criteria.setIncludeClosed(false);
		criteria.setAccountClass(0);
		final String whereClause = criteria.toWhereCause();

		final StringBuilder query = new StringBuilder("SELECT DEP.CID,CIF.XNAME,DEP.GRP,DEP.TYPE,DEP.CRCD,DEP.BAL FROM DEP,RELCIF,CIF WHERE CIF.ACN=RELCIF.ACN AND RELCIF.CID=DEP.CID AND DEP.COLELG=1 ").append(whereClause);
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, query.toString());
			rs = profile.executeQuery();
			while (rs.next()) {
				final DepositAccount account = new DepositAccount();
				account.setNumber(rs.getString("DEP.CID"));
				account.setReportName(rs.getString("CIF.XNAME"));
				account.setType(rs.getString("DEP.TYPE"));
				account.setGroup(rs.getString("DEP.GRP"));
				account.setCurrencyCode(rs.getString("DEP.CRCD"));
				account.setBalance(rs.getDouble("DEP.BAL"));

				// Set COLATTRIB for each column if protection is enabled.  
				if (Application.isDataItemProtection()) {
					account.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
				accounts.add(account);
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "findCollateralEligibleAccounts()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return accounts;
	}

	public static Map<String, Map<String, CollateralCode>> getCollateralCodesByGroup() {
		return collateralCodesByGroup;
	}

	public static boolean isCollateralReleased(final Agent agent, final LoanAccount account, final String collateralId) throws SanchezException {
		final String selectQuery = "SELECT COLCD FROM LN WHERE CID = ?";
		final String query = "SELECT AVRSL FROM COL WHERE COLL = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		boolean flag = false;

		try {

			// Check if unsecured
			profile = new ProfileSQL(agent, selectQuery);
			profile.setString(1, account.getNumberMap().get(account.getNumber()) == null ? account.getNumber() : account.getNumberMap().get(account.getNumber()));
			rs = profile.executeQuery();

			if (rs.next() && rs.getString("COLCD") != null) {
				final CollateralCode code = collateralCodesByGroup.get(account.getGroup()).get(rs.getString("COLCD"));
				if (code != null && Integer.parseInt(code.getType()) == 0) {
					flag = true;
				}
			}

			if (profile != null) {
				profile.close(rs);
			}

			if (!flag && collateralId != null) {

				//Check for MayBeReleased flag
				profile = new ProfileSQL(agent, query);
				profile.setString(1, collateralId);
				rs = profile.executeQuery();
				if (rs.next()) {
					flag = rs.getBoolean("AVRSL");
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "isCollateralReleased()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return flag;
	}

	public static void populateCollateralInsurances(final Agent agent, final LoanAccount account) {
		final String query = "SELECT COLL, POLNUM, INSTYP, CONAM, EXPDT, RENDT, CADDR1, CADDR2, CCITY, CPROVCD, CCNTRY, CPOSCD, AGNTNAM, AGNTPH, NFIPCMN, NFIPCMNUM, FEMACNUM, NFIPCMPNUM, NFIPMPDT, LOMADT, FLDZNCD, NFIPFLDINSAV, NFIPRP, NFIPEP, CBRAOPA, FLDHZDET, FLDINSREQ, LSTNOTDT, DSLNOT, HINS FROM COLINS WHERE COLL = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;

		for (final Collateral coll : Utility.protectNull(account.getCollaterals())) {
			if (coll.getId() != null) {
				final List<CollateralInsurance> insurances = new ArrayList<CollateralInsurance>(20);
				try {

					// Close through each for-loop iteration
					if (profile != null) {
						profile.close(rs);
					}

					profile = new ProfileSQL(agent, query);
					profile.setString(1, coll.getId());
					rs = profile.executeQuery();
					while (rs.next()) {
						final CollateralInsurance insurance = new CollateralInsurance();
						insurance.setPolicyNumber(rs.getString("POLNUM"));
						insurance.setModifiedPolicyNumber(rs.getString("POLNUM"));
						insurance.setInsuranceType(rs.getString("INSTYP"));
						insurance.setCompanyName(rs.getString("CONAM"));
						insurance.setExpirationDate(rs.getDate("EXPDT"));
						insurance.setRenewalDate(rs.getDate("RENDT"));

						final Address companyAddr = new Address();
						companyAddr.setAddressLine1(rs.getString("CADDR1"));
						companyAddr.setAddressLine2(rs.getString("CADDR2"));
						companyAddr.setCity(rs.getString("CCITY"));
						companyAddr.setState(rs.getString("CPROVCD"));
						companyAddr.setCountry(rs.getString("CCNTRY"));
						companyAddr.setZipCode(rs.getString("CPOSCD"));

						insurance.setCompanyAddress(companyAddr);
						insurance.setAgentName(rs.getString("AGNTNAM"));
						insurance.setAgentTelephoneNumber(rs.getString("AGNTPH"));
						insurance.setCommunityName(rs.getString("NFIPCMN"));
						insurance.setCommunityNumber(rs.getString("NFIPCMNUM"));
						insurance.setCertificateNumber(rs.getString("FEMACNUM"));
						insurance.setMapNumber(rs.getString("NFIPCMPNUM"));
						insurance.setMapPanelDate(rs.getDate("NFIPMPDT"));
						insurance.setAmendmentRevisionDate(rs.getDate("LOMADT"));
						insurance.setFloodZoneCode(rs.getString("FLDZNCD"));
						insurance.setInsuranceAvailable(rs.getBoolean("NFIPFLDINSAV"));
						insurance.setRegularProgram(rs.getBoolean("NFIPRP"));
						insurance.setEmergencyProgram(rs.getBoolean("NFIPEP"));
						insurance.setFloodCBRA_OPAArea(rs.getBoolean("CBRAOPA"));
						insurance.setSpecialHazardArea(rs.getBoolean("FLDHZDET"));
						insurance.setInsuranceRequired(rs.getBoolean("FLDINSREQ"));
						insurance.setLastNotificationDate(rs.getDate("LSTNOTDT"));
						insurance.setDaysSinceNotification(rs.getInt("DSLNOT"));
						insurance.setHazardInsuranceOnFile(rs.getString("HINS"));

						// Set COLATTRIB for each column if protection is enabled.  
						if (Application.isDataItemProtection()) {
							insurance.setProtectionRow(Utility.getTableColumnNamesList(rs));
						}

						insurances.add(insurance);
					}

					coll.setCollateralInsurances(insurances);
				} catch (final SQLException e) {
					Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "populateCollateralInsurances()", e.getMessage()});
				} finally {
					if (profile != null) {
						profile.close(rs);
					}
				}
			}
		}
	}

	public static void addCollateralInsurance(final Agent agent, final CollateralInsurance insurance, final Collateral collateral, final LoanAccount account) throws SanchezException {
		final String query = "INSERT INTO COLINS (COLL, POLNUM, INSTYP, EXPDT, RENDT, CONAM, CADDR1, CADDR2, CCITY, CPROVCD, CCNTRY, CPOSCD, AGNTNAM, AGNTPH, NFIPFLDINSAV, NFIPRP, NFIPEP, CBRAOPA, FLDHZDET, FLDINSREQ, LSTNOTDT, NFIPCMN, NFIPCMNUM, FEMACNUM, NFIPCMPNUM, NFIPMPDT, LOMADT, FLDZNCD, HINS) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, Utility.toProfileString(collateral.getId()));
			profile.setString(2, Utility.toProfileString(insurance.getModifiedPolicyNumber()));
			profile.setString(3, Utility.toProfileString(insurance.getInsuranceType()));
			profile.setString(4, Utility.toProfileDateString(insurance.getExpirationDate()));
			profile.setString(5, Utility.toProfileDateString(insurance.getRenewalDate()));
			profile.setString(6, Utility.toProfileString(insurance.getCompanyName()));
			profile.setString(7, Utility.toProfileString(insurance.getCompanyAddress().getAddressLine1()));
			profile.setString(8, Utility.toProfileString(insurance.getCompanyAddress().getAddressLine2()));
			profile.setString(9, Utility.toProfileString(insurance.getCompanyAddress().getCity()));
			profile.setString(10, Utility.toProfileString(insurance.getCompanyAddress().getState()));
			profile.setString(11, Utility.toProfileString(insurance.getCompanyAddress().getCountry()));
			profile.setString(12, Utility.toProfileString(insurance.getCompanyAddress().getZipCode()));
			profile.setString(13, Utility.toProfileString(insurance.getAgentName()));
			profile.setString(14, Utility.toProfileString(insurance.getAgentTelephoneNumber()));
			profile.setBoolean(15, insurance.isInsuranceAvailable());
			profile.setBoolean(16, insurance.isRegularProgram());
			profile.setBoolean(17, insurance.isEmergencyProgram());
			profile.setBoolean(18, insurance.isFloodCBRA_OPAArea());
			profile.setBoolean(19, insurance.isSpecialHazardArea());
			profile.setBoolean(20, insurance.isInsuranceRequired());
			profile.setString(21, Utility.toProfileDateString(insurance.getLastNotificationDate()));
			profile.setString(22, Utility.toProfileString(insurance.getCommunityName()));
			profile.setString(23, Utility.toProfileString(insurance.getCommunityNumber()));
			profile.setString(24, Utility.toProfileString(insurance.getCertificateNumber()));
			profile.setString(25, Utility.toProfileString(insurance.getMapNumber()));
			profile.setString(26, Utility.toProfileDateString(insurance.getMapPanelDate()));
			profile.setString(27, Utility.toProfileDateString(insurance.getAmendmentRevisionDate()));
			profile.setString(28, Utility.toProfileString(insurance.getFloodZoneCode()));
			profile.setString(29, Utility.toProfileString(insurance.getHazardInsuranceOnFile()));
			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "CollateralManager", "addCollateralInsurance()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	public static void updateCollateralInsurance(final Agent agent, final CollateralInsurance insurance, final Collateral collateral, final LoanAccount account) {
		final String query = "UPDATE COLINS SET EXPDT = ?, RENDT = ?, CONAM = ?, CADDR1 = ?, CADDR2 = ?, CCITY = ?, CPROVCD = ?, CCNTRY = ?, CPOSCD = ?, AGNTNAM = ?, AGNTPH = ?, NFIPFLDINSAV = ?, NFIPRP = ?, NFIPEP = ?, CBRAOPA = ?, FLDHZDET = ?, FLDINSREQ = ?, LSTNOTDT = ?, NFIPCMN = ?, NFIPCMNUM = ?, FEMACNUM = ?, NFIPCMPNUM = ?, NFIPMPDT = ?, LOMADT = ?, FLDZNCD = ?, HINS = ?, INSTYP = ?, POLNUM = ? WHERE COLL = ? AND POLNUM = ?";
		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, Utility.toProfileDateString(insurance.getExpirationDate()));
			profile.setString(2, Utility.toProfileDateString(insurance.getRenewalDate()));
			profile.setString(3, Utility.toProfileString(insurance.getCompanyName()));
			profile.setString(4, Utility.toProfileString(insurance.getCompanyAddress().getAddressLine1()));
			profile.setString(5, Utility.toProfileString(insurance.getCompanyAddress().getAddressLine2()));
			profile.setString(6, Utility.toProfileString(insurance.getCompanyAddress().getCity()));
			profile.setString(7, Utility.toProfileString(insurance.getCompanyAddress().getState()));
			profile.setString(8, Utility.toProfileString(insurance.getCompanyAddress().getCountry()));
			profile.setString(9, Utility.toProfileString(insurance.getCompanyAddress().getZipCode()));
			profile.setString(10, Utility.toProfileString(insurance.getAgentName()));
			profile.setString(11, Utility.toProfileString(insurance.getAgentTelephoneNumber()));
			profile.setBoolean(12, insurance.isInsuranceAvailable());
			profile.setBoolean(13, insurance.isRegularProgram());
			profile.setBoolean(14, insurance.isEmergencyProgram());
			profile.setBoolean(15, insurance.isFloodCBRA_OPAArea());
			profile.setBoolean(16, insurance.isSpecialHazardArea());
			profile.setBoolean(17, insurance.isInsuranceRequired());
			profile.setString(18, Utility.toProfileDateString(insurance.getLastNotificationDate()));
			profile.setString(19, Utility.toProfileString(insurance.getCommunityName()));
			profile.setString(20, Utility.toProfileString(insurance.getCommunityNumber()));
			profile.setString(21, Utility.toProfileString(insurance.getCertificateNumber()));
			profile.setString(22, Utility.toProfileString(insurance.getMapNumber()));
			profile.setString(23, Utility.toProfileDateString(insurance.getMapPanelDate()));
			profile.setString(24, Utility.toProfileDateString(insurance.getAmendmentRevisionDate()));
			profile.setString(25, Utility.toProfileString(insurance.getFloodZoneCode()));
			profile.setString(26, Utility.toProfileString(insurance.getHazardInsuranceOnFile()));
			profile.setString(27, Utility.toProfileString(insurance.getInsuranceType()));
			profile.setString(28, Utility.toProfileString(insurance.getModifiedPolicyNumber()));

			profile.setString(29, Utility.toProfileString(collateral.getId()));
			profile.setString(30, Utility.toProfileString(insurance.getPolicyNumber()));
			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "CollateralManager", "updateCollateralInsurance()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	public static void deleteCollateralInsurance(final Agent agent, final LoanAccount account, final Collateral collateral, final CollateralInsurance insurance) throws SanchezException {

		// Cannot delete unidentified collateral insurance.
		if (!Utility.hasText(insurance.getPolicyNumber())) {
			return;
		}

		final String query = "DELETE FROM COLINS WHERE COLL = ? AND POLNUM = ?";
		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, collateral.getId());
			profile.setString(2, insurance.getPolicyNumber());
			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "CollateralManager", "deleteCollateralInsurance()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	public static void populateLoanExternalLienDetail(final Agent agent, final Collateral coll) {
		final String query = "SELECT COLL, PSRLNBAL, FMB, FPLENREFNUM, FPLENSTADDR1, FPLENSTADDR2, FPLENCITY, FPLENPROV, FPLENCNTRY, FPLENPCD, FPLENPHNUM, FPLENREP, SSRLNBAL, SPLENNAME, SPLENREFNUM, SPLENSTADDR1, SPLENSTADDR2, SPLENCITY, SPLENPROV, SPLENCNTRY, SPLENPCD, SPLENPHNUM, SPLENREP FROM COL WHERE COL.COLL = ?";
		ProfileSQL profile = null;
		ResultSet rs = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, coll.getId());
			rs = profile.executeQuery();
			if (rs.next()) {
				coll.setPrimaryLienBalance(rs.getBigDecimal("PSRLNBAL") == null ? null : rs.getBigDecimal("PSRLNBAL"));
				coll.setFirstPositionLenderName(rs.getString("FMB"));
				coll.setFirstPositionLenderReferenceNumber(rs.getString("FPLENREFNUM"));
				coll.getFirstPositionLenderAddress().setAddressLine1(rs.getString("FPLENSTADDR1"));
				coll.getFirstPositionLenderAddress().setAddressLine2(rs.getString("FPLENSTADDR2"));
				coll.getFirstPositionLenderAddress().setCity(rs.getString("FPLENCITY"));
				coll.getFirstPositionLenderAddress().setState(rs.getString("FPLENPROV"));
				coll.getFirstPositionLenderAddress().setCountry(rs.getString("FPLENCNTRY"));
				coll.getFirstPositionLenderAddress().setZipCode(rs.getString("FPLENPCD"));
				coll.setFirstPositionLenderPhoneNumber(rs.getString("FPLENPHNUM"));
				coll.setFirstPositionLenderRepresentative(rs.getString("FPLENREP"));
				coll.setSecondaryLienBalance(rs.getBigDecimal("SSRLNBAL") == null ? null : rs.getBigDecimal("SSRLNBAL"));
				coll.setSecondPositionLenderName(rs.getString("SPLENNAME"));
				coll.setSecondPositionLenderReferenceNumber(rs.getString("SPLENREFNUM"));
				coll.getSecondPositionLenderAddress().setAddressLine1(rs.getString("SPLENSTADDR1"));
				coll.getSecondPositionLenderAddress().setAddressLine2(rs.getString("SPLENSTADDR2"));
				coll.getSecondPositionLenderAddress().setCity(rs.getString("SPLENCITY"));
				coll.getSecondPositionLenderAddress().setState(rs.getString("SPLENPROV"));
				coll.getSecondPositionLenderAddress().setCountry(rs.getString("SPLENCNTRY"));
				coll.getSecondPositionLenderAddress().setZipCode(rs.getString("SPLENPCD"));
				coll.setSecondPositionLenderPhoneNumber(rs.getString("SPLENPHNUM"));
				coll.setSecondPositionLenderRepresentative(rs.getString("SPLENREP"));

				// Set COLATTRIB for each column if protection is enabled.  
				if (Application.isDataItemProtection()) {
					coll.setProtectionRow(Utility.getTableColumnNamesList(rs));
				}
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "populateLoanExternalLienDetail()", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
	}

	public static void updateExternalLienInformation(final Agent agent, final Collateral collateral) throws SanchezException {
		final String query = "UPDATE COL SET PSRLNBAL = ?, FMB = ?, FPLENREFNUM = ?, FPLENSTADDR1 = ?, FPLENSTADDR2 = ?, FPLENCITY = ?, FPLENPROV = ?, FPLENCNTRY = ?, FPLENPCD = ?, FPLENPHNUM = ?, FPLENREP = ?, SSRLNBAL = ?, SPLENNAME = ?, SPLENREFNUM = ?, SPLENSTADDR1 = ?, SPLENSTADDR2 = ?, SPLENCITY = ?, SPLENPROV = ?, SPLENCNTRY = ?, SPLENPCD = ?, SPLENPHNUM = ?, SPLENREP = ? WHERE COLL = ?";
		ProfileSQL profile = null;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, collateral.getPrimaryLienBalance() == null ? null : String.valueOf(collateral.getPrimaryLienBalance()));
			profile.setString(2, Utility.toProfileString(collateral.getFirstPositionLenderName()));
			profile.setString(3, Utility.toProfileString(collateral.getFirstPositionLenderReferenceNumber()));
			profile.setString(4, Utility.toProfileString(collateral.getFirstPositionLenderAddress().getAddressLine1()));
			profile.setString(5, Utility.toProfileString(collateral.getFirstPositionLenderAddress().getAddressLine2()));
			profile.setString(6, Utility.toProfileString(collateral.getFirstPositionLenderAddress().getCity()));
			profile.setString(7, Utility.toProfileString(collateral.getFirstPositionLenderAddress().getState()));
			profile.setString(8, Utility.toProfileString(collateral.getFirstPositionLenderAddress().getCountry()));
			profile.setString(9, Utility.toProfileString(collateral.getFirstPositionLenderAddress().getZipCode()));
			profile.setString(10, Utility.toProfileString(collateral.getFirstPositionLenderPhoneNumber()));
			profile.setString(11, Utility.toProfileString(collateral.getFirstPositionLenderRepresentative()));

			profile.setString(12, collateral.getSecondaryLienBalance() == null ? null : String.valueOf(collateral.getSecondaryLienBalance()));
			profile.setString(13, Utility.toProfileString(collateral.getSecondPositionLenderName()));
			profile.setString(14, Utility.toProfileString(collateral.getSecondPositionLenderReferenceNumber()));
			profile.setString(15, Utility.toProfileString(collateral.getSecondPositionLenderAddress().getAddressLine1()));
			profile.setString(16, Utility.toProfileString(collateral.getSecondPositionLenderAddress().getAddressLine2()));
			profile.setString(17, Utility.toProfileString(collateral.getSecondPositionLenderAddress().getCity()));
			profile.setString(18, Utility.toProfileString(collateral.getSecondPositionLenderAddress().getState()));
			profile.setString(19, Utility.toProfileString(collateral.getSecondPositionLenderAddress().getCountry()));
			profile.setString(20, Utility.toProfileString(collateral.getSecondPositionLenderAddress().getZipCode()));
			profile.setString(21, Utility.toProfileString(collateral.getSecondPositionLenderPhoneNumber()));
			profile.setString(22, Utility.toProfileString(collateral.getSecondPositionLenderRepresentative()));

			profile.setString(23, collateral.getId());

			profile.executeUpdate();
		} catch (final SQLException e) {
			Logger.warn("exception", new String[] {e.getClass().getName(), "CollateralManager", "updateExternalLienInformation()", e.getMessage()});
			throw ProfileSQL.toSanchezException(e);
		} finally {
			if (profile != null) {
				profile.close();
			}
		}
	}

	private static String generateCollateralId(final Agent agent, final String accountNumber) throws SanchezException {
		final String query = "SELECT COLL FROM COL WHERE COLL >= ? AND COLL < ? ORDER BY COLL DESC";
		ProfileSQL profile = null;
		ResultSet rs = null;
		final String formatAccountNumber = String.format("%-12s", accountNumber).replace(' ', '0');
		final String collateralIdMinimum = formatAccountNumber + "000000";
		final String collateralIdMaximum = formatAccountNumber + "999999";
		String collateralId = collateralIdMinimum;
		try {
			profile = new ProfileSQL(agent, query);
			profile.setString(1, Utility.toProfileString(collateralIdMinimum));
			profile.setString(2, Utility.toProfileString(collateralIdMaximum));
			rs = profile.executeQuery();
			if (rs.next()) {
				collateralId = Long.toString(rs.getLong("COLL") + 1);
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "CollateralManager", "generateCollateralId()", e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		return collateralId;
	}
}