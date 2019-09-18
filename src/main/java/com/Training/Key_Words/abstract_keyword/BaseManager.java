// EXAMPLE OF abstract CLASS

package com.fisglobal.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fisglobal.base.Logger;
import com.fisglobal.host.ProfileSQL;

public abstract class BaseManager {
	public static Map<String, String> fillMap(final String table, final String keyField, final String valueField, final boolean sortByValue) {
		return fillMap(table, keyField, valueField, sortByValue, null, null);
	}

	public static Map<String, String> fillMap(final String table, final String keyField, final String valueField, final boolean sortByValue, final String groupField, final String group) {
		final Map<String, String> map = new LinkedHashMap<String, String>();
		ProfileSQL profile = null;
		ResultSet rs = null;
		final StringBuilder query = new StringBuilder("SELECT ").append(keyField).append(",").append(valueField).append(" FROM ").append(table);
		if (group != null && groupField != null) {
			query.append(" WHERE ").append(groupField).append(" = '").append(group).append("'");
		}
		if (sortByValue) {
			query.append(" ORDER BY ").append(valueField);
		} else {
			query.append(" ORDER BY ").append(keyField);
		}
		try {
			profile = new ProfileSQL(true);
			rs = profile.executeQuery(query.toString());
			while (rs.next()) {
				map.put(rs.getString(1), rs.getString(2));
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "BaseManager", "fillMap() - Table: " + table, e.getMessage()});
		} finally {
			Logger.info("Caching table: " + table + " (" + map.size() + ")");
			if (profile != null) {
				profile.close(rs);
			}
		}
		return map;
	}

	public static Map<String, Map<String, String>> fillMapOfMaps(final String table, final String firstKey, final String secondKey, final String description) {
		final Map<String, Map<String, String>> map = new LinkedHashMap<String, Map<String, String>>();
		ProfileSQL profile = null;
		ResultSet rs = null;
		final StringBuilder query = new StringBuilder("SELECT ");
		query.append(description).append(",").append(firstKey).append(",").append(secondKey).append(" FROM ").append(table);
		try {
			profile = new ProfileSQL(true);
			rs = profile.executeQuery(query.toString());
			while (rs.next()) {
				final String productGroup = rs.getString(firstKey);
				if (!map.containsKey(productGroup)) {
					map.put(productGroup, new LinkedHashMap<String, String>());
				}
				map.get(productGroup).put(rs.getString(secondKey), rs.getString(description));
			}
		} catch (final SQLException e) {
			Logger.error("exception", new String[] {e.getClass().getName(), "BaseManager", "fill" + table, e.getMessage()});
		} finally {
			if (profile != null) {
				profile.close(rs);
			}
		}
		Logger.info("Caching table: " + table + " (" + map.size() + ")");
		return map;
	}
}