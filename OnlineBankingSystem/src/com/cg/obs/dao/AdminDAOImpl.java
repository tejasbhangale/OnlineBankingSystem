package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transactions;
import com.cg.obs.bean.User;
import com.cg.obs.exception.JDBCConnectionError;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.ConnectionProvider;
import com.cg.obs.util.Messages;

public class AdminDAOImpl implements IAdminDAO {

	@Override
	public boolean addAccountDetails(Customer cust) throws JDBCConnectionError {
		
		
				
				
		int status = 0;
		
		
		try(Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn.prepareStatement(IQueryMapper.INSERT_ACCOUNT_DETAILS);) {
			
			pstm.setLong(1, cust.getAccountId());
			pstm.setString(2, cust.getCustomerName());
            pstm.setLong(3, cust.getMobile());
            pstm.setString(4, cust.getEmail());
            pstm.setString(5, cust.getAddress());
            pstm.setString(6, cust.getPancard());
            
            status = pstm.executeUpdate();
            
            System.out.println(status);
            
		} catch (SQLException e ) {

          throw new JDBCConnectionError(Messages.CONNECTION_ESTABILISHED_FAILURE);
			
		} catch (OnlineBankingException e1) {
			
	          throw new JDBCConnectionError(Messages.CONNECTION_ESTABILISHED_FAILURE);

		}
		
		if(status>0)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean addAccountMaster(AccountMaster account) throws JDBCConnectionError {
		
		
		int status = 0;
		

		try(Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn.prepareStatement(IQueryMapper.INSERT_ACCOUNT_MASTER);) {
			
			pstm.setLong(1, account.getAccountId());
			pstm.setString(2, account.getAccountType());
            pstm.setDouble(3, account.getOpeningBalance());
            pstm.setDate(4, (Date) account.getOpenDate());
           
            
            status = pstm.executeUpdate();
            
            
				} catch (SQLException e ) {

			          throw new JDBCConnectionError(Messages.CONNECTION_ESTABILISHED_FAILURE);
						
					} catch (OnlineBankingException e1) {
						
				          throw new JDBCConnectionError(Messages.CONNECTION_ESTABILISHED_FAILURE);

					}
		
		if(status>0)
		{
			return true;
		}
		
		
		return false;
	}


	@Override
	public List<Transactions> getTransactionDetails(
			Date startDate, Date endDate) throws JDBCConnectionError {
		
		List<Transactions> list = new ArrayList<Transactions>();

		


		try(Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn.prepareStatement(IQueryMapper.GET_TRANSACTION_DETAILS);) {
			
			
			
			pstm.setDate(1, startDate);
			pstm.setDate(2, endDate);

            
            ResultSet result =  pstm.executeQuery();
            
       
            
            while(result.next())
            {
            	Transactions tran = new Transactions();
            
            	tran.setTransactionId(result.getLong(1));
            	tran.setTransactionDesc(result.getString(2));
            	tran.setDateOfTransaction(result.getDate(3));
            	tran.setTransactionType(result.getString(4));
            	tran.setTransactionAmount(result.getDouble(5));
            	tran.setAccountId(result.getLong(6));
            	list.add(tran);
            	
            	
            }
            
            
            
		} catch (SQLException e ) {

	          throw new JDBCConnectionError(Messages.CONNECTION_ESTABILISHED_FAILURE);
				
			} catch (OnlineBankingException e1) {
				
		          throw new JDBCConnectionError(Messages.CONNECTION_ESTABILISHED_FAILURE);

			}
		
	
		
		return list;
	}

	@Override
	public User getSecretQuestionAnswer(int accNumber) throws JDBCConnectionError {
		
		User user = null;

		try(Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn.prepareStatement(IQueryMapper.GET_SECRET_QUESTION_ANSWER);) {
			
			
			pstm.setInt(1, accNumber);
			
			ResultSet result = pstm.executeQuery();
			
			
			 while(result.next())
	            {
	            	user = new User();
	            	user.setAccountId(result.getInt(1));
	            	user.setUserId(result.getInt(2));
	            	user.setSecretAnswer(result.getString(4));
	            	user.setSecretQuestion(result.getString(5));
	            	user.setLockStatus(result.getString(7).charAt(0));
	            	
	            }
			
		
		} catch (SQLException e ) {

	          throw new JDBCConnectionError(Messages.CONNECTION_ESTABILISHED_FAILURE);
				
			} catch (OnlineBankingException e1) {
				
		          throw new JDBCConnectionError(Messages.CONNECTION_ESTABILISHED_FAILURE);

			}
		
		return user;
	}

	@Override
	public boolean changeAccountStatus(int accNumber) throws JDBCConnectionError {
		
		int status = 0;

		try(Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn.prepareStatement(IQueryMapper.CHANGE_ACCOUNT_STATUS);) {
			
			pstm.setString(1, "u");
			pstm.setInt(2, accNumber);
			
			 status = pstm.executeUpdate();
			
		
		} catch (SQLException e ) {

	          throw new JDBCConnectionError(Messages.CONNECTION_ESTABILISHED_FAILURE);
				
			} catch (OnlineBankingException e1) {
				
		          throw new JDBCConnectionError(Messages.CONNECTION_ESTABILISHED_FAILURE);

			}
		
		if(status==1)
		{
			return true;
		}
		
		
		return false;
	}

	
}
