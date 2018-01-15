package com.infobox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public final class commonLib {
	
	
	//------------------------------------------------------------------------------------
	public static String nvl(String val, String valifnull) {
		if (val==null || val.length()==0) return valifnull;
		else return val;
	} 
	
	
	
	//------------------------------------------------------------------------------------
	static public Connection getConn(String driver, String url, String user, String pass) {
		Connection conn=null;
		/*
		System.out.println("Connecting to : ");
		System.out.println("driver     :["+driver+"]");
		System.out.println("connstr    :["+url+"]");
		System.out.println("user       :["+user+"]");
		System.out.println("pass       :["+"************]");	
		*/
			try {
				Class.forName(driver.replace("*",""));
				conn = DriverManager.getConnection(url, user, pass);
				
			
				return conn;
					
	
			} catch (Exception e) {
				System.out.println("Exception@getconn : " + e.getMessage());
				e.printStackTrace();
				return null;
			}
	}
	
	
	static public final String DEFAULT_DATE_FORMAT="dd/MM/yyyy HH:mm:ss";
	
	//---------------------------------------------------------------------------------------
	public static ArrayList<String[]> getDbArray(
			Connection conn, 
			String sql, 
			int limit,
			ArrayList<String[]> bindlist, 
			int timeout_insecond,
			ArrayList<String> colNameList,
			ArrayList<String> colTypeList) {
		
		ArrayList<String[]> ret1 = new ArrayList<String[]>();

		PreparedStatement pstmtConf = null;
		ResultSet rsetConf = null;
		ResultSetMetaData rsmdConf = null;

		
		int reccnt = 0;
		try {
			if (pstmtConf == null) 	{
				pstmtConf = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				try {pstmtConf.setFetchSize(1000);} catch(Exception e) {}
			}
			
			
			//------------------------------ end binding

			if (bindlist!=null) {
				for (int i = 1; i <= bindlist.size(); i++) {
					String[] a_bind = bindlist.get(i - 1);
					String bind_type = a_bind[0];
					String bind_val = a_bind[1];
					
	
					if (bind_type.equals("INTEGER")) {
						if (bind_val == null || bind_val.equals(""))
							pstmtConf.setNull(i, java.sql.Types.INTEGER);
						else
							pstmtConf.setInt(i, Integer.parseInt(bind_val));
					} else if (bind_type.equals("LONG")) {
						if (bind_val == null || bind_val.equals(""))
							pstmtConf.setNull(i, java.sql.Types.INTEGER);
						else
							pstmtConf.setLong(i, Long.parseLong(bind_val));
					} else if (bind_type.equals("DOUBLE")) {
						if (bind_val == null || bind_val.equals(""))
							pstmtConf.setNull(i, java.sql.Types.DOUBLE);
						else
							pstmtConf.setDouble(i, Double.parseDouble(bind_val));
					} else if (bind_type.equals("FLOAT")) {
						if (bind_val == null || bind_val.equals(""))
							pstmtConf.setNull(i, java.sql.Types.FLOAT);
						else
							pstmtConf.setFloat(i, Float.parseFloat(bind_val));
					}  else if (bind_type.equals("DATE")) {
						if (bind_val == null || bind_val.equals(""))
							pstmtConf.setNull(i, java.sql.Types.DATE);
						else {
							Date d = new SimpleDateFormat(DEFAULT_DATE_FORMAT)
									.parse(bind_val);
							java.sql.Date date = new java.sql.Date(d.getTime());
							pstmtConf.setDate(i, date);
						}
					} 
					else if (bind_type.equals("TIMESTAMP")) {
						if (bind_val == null || bind_val.equals(""))
							pstmtConf.setNull(i, java.sql.Types.TIMESTAMP);
						else {
							Timestamp ts=new Timestamp(System.currentTimeMillis());
							try {ts=new Timestamp(Long.parseLong(bind_val));} catch(Exception e) {e.printStackTrace();}
							pstmtConf.setTimestamp(i, ts);
						}
					}
					else {
						pstmtConf.setString(i, bind_val);
					}
				}
				//------------------------------ end binding
			}  // if bindlist 
			
			if (timeout_insecond>0 )
				pstmtConf.setQueryTimeout(timeout_insecond);
			
			if (rsetConf == null) rsetConf = pstmtConf.executeQuery();
			
			
			
			if (rsmdConf == null) rsmdConf = rsetConf.getMetaData();
			
			
			int colcount = rsmdConf.getColumnCount();
			
			if (colNameList!=null || colTypeList!=null) {
				if (colNameList!=null) colNameList.clear();
				if (colTypeList!=null) colTypeList.clear();
				
				for (int i=1;i<=colcount;i++) {
					String col_name=rsmdConf.getColumnLabel(i);
					String col_type=rsmdConf.getColumnTypeName(i);
					
					if (colNameList!=null)  colNameList.add(col_name);
					if (colTypeList!=null)  colTypeList.add(col_type);
				}
			}
			
			
			String a_field = "";
			
			
			
			while (rsetConf.next()) {
				reccnt++;
				if (reccnt > limit) break;
				String[] row = new String[colcount];
				for (int i = 1; i <= colcount; i++) {
					try {
						a_field = rsetConf.getString(i);
						
						if (a_field.equals("null")) a_field=""; 
						if (a_field.length()>100000) a_field=a_field.substring(0,100000);
						} 
					catch (Exception enull) {a_field = "";}
					row[i - 1] = a_field;
				}
				ret1.add(row);
			}
			
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("Exception@getDbArray : SQL       => " + sql);
			System.out.println("Exception@getDbArray : MSG       => " + sqle.getMessage());
			System.out.println("Exception@getDbArray : CODE      => " + sqle.getErrorCode());
			System.out.println("Exception@getDbArray : SQL STATE => " + sqle.getSQLState());
		}
		catch (Exception ignore) {
			ignore.printStackTrace();
			System.out.println("Exception@getDbArray : SQL => " + sql);
			System.out.println("Exception@getDbArray : MSG => " + ignore.getMessage());
		} finally {
			try {rsetConf.close();} catch(Exception e) { e.printStackTrace();}
			try {pstmtConf.close();} catch(Exception e) {e.printStackTrace();}
			
		}
		return ret1;
	}
	
	
	
	//****************************************
	static public boolean execSingleUpdateSQL(
			Connection conn, 
			String sql,
			ArrayList<String[]> bindlist, 
			boolean commit_after, 
			int timeout_as_sec,
			StringBuilder sberr
			) {

		boolean ret1 = true;
		PreparedStatement pstmt_execbind = null;

		StringBuilder using = new StringBuilder();
		try {
			pstmt_execbind = conn.prepareStatement(sql);

			if (timeout_as_sec>0) 
				try { pstmt_execbind.setQueryTimeout(timeout_as_sec);  } catch(Exception e) {}
			if (bindlist!=null)
			for (int i = 1; i <= bindlist.size(); i++) {
				String[] a_bind = bindlist.get(i - 1);
				String bind_type = a_bind[0];
				String bind_val = a_bind[1];
				if (i > 1)
					using.append(", ");
				using.append("{" + bind_val + "}");

				if (bind_type.equals("INTEGER")) {
					if (bind_val == null || bind_val.equals(""))
						pstmt_execbind.setNull(i, java.sql.Types.INTEGER);
					else
						pstmt_execbind.setInt(i, Integer.parseInt(bind_val));
				} else if (bind_type.equals("LONG")) {
					if (bind_val == null || bind_val.equals(""))
						pstmt_execbind.setNull(i, java.sql.Types.INTEGER);
					else
						pstmt_execbind.setLong(i, Long.parseLong(bind_val));
				} else if (bind_type.equals("DATE")) {
					if (bind_val == null || bind_val.equals(""))
						pstmt_execbind.setNull(i, java.sql.Types.DATE);
					else {
						Date d = new SimpleDateFormat(DEFAULT_DATE_FORMAT)
								.parse(bind_val);
						java.sql.Date date = new java.sql.Date(d.getTime());
						pstmt_execbind.setDate(i, date);
					}
				} 
				else if (bind_type.equals("TIMESTAMP")) {
					if (bind_val == null || bind_val.equals(""))
						pstmt_execbind.setNull(i, java.sql.Types.TIMESTAMP);
					else {
						Timestamp ts=new Timestamp(System.currentTimeMillis());
						try {ts=new Timestamp(Long.parseLong(bind_val));} catch(Exception e) {e.printStackTrace();}
						pstmt_execbind.setTimestamp(i, ts);
					}
				}
				else {
					pstmt_execbind.setString(i, bind_val);
				}
			}

			//System.out.println("Executing SQL : " + sql + " using " + using.toString());
			if (sberr!=null) sberr.append("Executing SQL : " + sql + " using " + using.toString());

			pstmt_execbind.executeUpdate();
			//pstmt_execbind.execute();
			
			if (!conn.getAutoCommit() && commit_after) 	{
				conn.commit();
			}


		} catch (Exception e) {
			System.out.println("Exception@execSingleUpdateSQL : " + e.getMessage());
			e.printStackTrace();
			ret1 = false;
		} finally {
			try {
				pstmt_execbind.close();
				pstmt_execbind = null;
			} catch (Exception e) {
			}
		}

		return ret1;
	}
	
	
	//--------------------------------------------------------------
	public static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
	
}
