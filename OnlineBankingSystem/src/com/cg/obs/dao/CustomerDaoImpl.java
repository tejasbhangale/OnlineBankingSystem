package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.ServiceTracker;
import com.cg.obs.bean.Transactions;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.exception.OnlineBankingException;
import com.cg.obs.util.ConnectionProvider;
import com.cg.obs.util.Messages;

public class CustomerDaoImpl implements ICustomerDao {

	@Override
	public Customer getCustomerDetails(int id) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_CUSTOMER_DETAILS);) {
			pt.setInt(1, id);
			ResultSet cusResSet = pt.executeQuery();

			while (cusResSet.next()) {
				Customer customer = new Customer();
				customer.setUserId(id);
				customer.setCustomerName(cusResSet.getString(2));
				customer.setMobile(cusResSet.getLong(3));
				customer.setEmail(cusResSet.getString(4));
				customer.setAddress(cusResSet.getString(5));
				customer.setPancard(cusResSet.getString(6));
				return customer;
			}

		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean updateCustomerDetails(Customer customer) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.UPDATE_CUSTOMER_DETAILS);) {
			pt.setLong(1, customer.getMobile());
			pt.setString(2, customer.getAddress());
			pt.setLong(3, customer.getUserId());

			int res = pt.executeUpdate();
			if (res >= 1)
				return true;

		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean checkOldPass(String oldPass, int userId) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.CHECK_OLD_PASSWORD);) {
			pt.setInt(1, userId);

			ResultSet res = pt.executeQuery();
			if (res.next()) {
				if (res.getString(1).equals(oldPass)) {
					return true;
				}
			}

		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updatePassword(String newPass, int userId)
			throws OnlineBankingException {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.UPDATE_CUSTOMER_PASSWORD);) {

			pt.setString(1, newPass);
			pt.setInt(2, userId);
			pt.executeUpdate();
		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int requestChequeBook(int accId) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GENERATE_SERVICE_REQUEST);
				PreparedStatement pt2 = conn
						.prepareStatement(IQueryMapper.GET_SERVICE_REQUEST_NUMBER);) {

			Date date = Date.valueOf(LocalDate.now());
			pt.setString(1, "New ChequeBook Request");
			pt.setInt(2, accId);
			pt.setDate(3, date);
			pt.setString(4, "Issued");

			int result = pt.executeUpdate();

			if (result >= 1) {
				ResultSet res = pt2.executeQuery();
				if (res.next()) {
					return res.getInt(1);
				} else {
					return 0;
				}
			} else {
				return 0;
			}

		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Integer> getAccountList(long id) {

		List<Integer> accountList = new ArrayList<Integer>();
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_SELF_ACCOUNTS);) {
			pt.setLong(1, id);

			ResultSet resultSet = pt.executeQuery();

			while (resultSet.next()) {
				accountList.add(resultSet.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (OnlineBankingException e1) {
			e1.printStackTrace();
		}
		return accountList;
	}

	@Override
	public List<Transactions> getMiniStatement(int ar)
			throws OnlineBankingException {

		List<Transactions> transaction = new ArrayList<>();

		int count = 1;

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.GET_MINI_STATEMENT);) {

			pstm.setInt(1, ar);

			ResultSet result = pstm.executeQuery();

			while (result.next() && count <= 10) {
				Transactions tran = new Transactions();

				tran.setTransactionId(result.getLong(1));
				tran.setTransactionDesc(result.getString(2));
				tran.setDateOfTransaction(result.getDate(3));
				tran.setTransactionType(result.getString(4));
				tran.setTransactionAmount(result.getDouble(5));
				tran.setAccountId(result.getLong(6));
				transaction.add(tran);
				count++;
			}
		} catch (SQLException e) {
			throw new OnlineBankingException(Messages.SQL_EXCEPTION_ERROR);
		}

		if (count == 1) {
			return null;
		}

		return transaction;
	}

	public ServiceTracker getRequestStatus(int reqNum, int userId) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_REQUEST_STATUS);) {

			pt.setInt(1, reqNum);
			pt.setInt(2, userId);

			ServiceTracker sTrack = null;
			ResultSet res = pt.executeQuery();
			while (res.next()) {
				sTrack = new ServiceTracker();
				sTrack.setService_id(res.getInt(1));
				sTrack.setServiceDescription(res.getString(2));
				sTrack.setAccountId(res.getInt(3));
				sTrack.setServiceRaisedDate(res.getDate(4));
				sTrack.setStatus(res.getString(5));
			}
			return sTrack;

		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<ServiceTracker> getAllRequestStatus(int accNum) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_ALL_REQUESTS);) {

			pt.setInt(1, accNum);

			ResultSet resSet = pt.executeQuery();
			ArrayList<ServiceTracker> reqList = new ArrayList<ServiceTracker>();
			int count = 0;
			while (resSet.next() && count < 20) {
				count++;
				ServiceTracker sTrack = new ServiceTracker();
				sTrack.setService_id(resSet.getInt(1));
				sTrack.setServiceDescription(resSet.getString(2));
				sTrack.setAccountId(resSet.getInt(3));
				sTrack.setServiceRaisedDate(resSet.getDate(4));
				sTrack.setStatus(resSet.getString(5));
				reqList.add(sTrack);
			}
			return reqList;
		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Transactions> getDetailedStatement(int ar, Date startDate,
			Date endDate) throws OnlineBankingException {

		List<Transactions> transaction = new ArrayList<>();

		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.GET_DETAILED_STATEMENT);) {

			pstm.setInt(1, ar);
			pstm.setDate(2, startDate);
			pstm.setDate(3, endDate);

			ResultSet result = pstm.executeQuery();

			while (result.next()) {
				Transactions tran = new Transactions();

				tran.setTransactionId(result.getLong(1));
				tran.setTransactionDesc(result.getString(2));
				tran.setDateOfTransaction(result.getDate(3));
				tran.setTransactionType(result.getString(4));
				tran.setTransactionAmount(result.getDouble(5));
				tran.setAccountId(result.getLong(6));
				transaction.add(tran);

			}

		} catch (SQLException e) {
			throw new OnlineBankingException(Messages.SQL_EXCEPTION_ERROR);
		}

		return transaction;
	}

	@Override
	public double getAccBalance(long accountId) {
		double balance = 0;
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pstm = conn
						.prepareStatement(IQueryMapper.GET_ACCOUNT_BALANCE);) {
			pstm.setLong(1, accountId);
			ResultSet resultSet = pstm.executeQuery();
			if (resultSet.next()) {
				balance = resultSet.getDouble(1);
			}

		} catch (SQLException | OnlineBankingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public List<Payee> getPayeeList(long id) {
		List<Payee> payeeList = new ArrayList<Payee>();
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_PAYEE_LIST);) {
			pt.setLong(1, id);

			ResultSet resultSet = pt.executeQuery();

			while (resultSet.next()) {
				Payee payee = new Payee();
				payee.setAccountId(resultSet.getLong(1));
				payee.setPayeeAccountId(resultSet.getLong(2));
				payee.setNickName(resultSet.getString(3));
				payeeList.add(payee);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (OnlineBankingException e1) {
			e1.printStackTrace();
		}
		return payeeList;
	}

	@Override
	public boolean debitFunds(long accountID, double transferAmount) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.DEBIT_FUNDS);) {
			pt.setDouble(1, transferAmount);
			pt.setLong(2, accountID);

			int res = pt.executeUpdate();
			if (res == 1){
				System.out.println("debited");
				return true;
			}
						} catch (OnlineBankingException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
		return false;
	}

	@Override
	public ArrayList<Integer> getAllAccounts(int userId) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_ALL_ACCOUNTS);) {

			pt.setInt(1, userId);

			ResultSet resSet = pt.executeQuery();
			ArrayList<Integer> accList = new ArrayList<Integer>();
			while (resSet.next()) {
				accList.add(resSet.getInt(1));
			}
			return accList;

		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean creditFunds(long accountID, double transferAmount) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.CREDIT_FUNDS);) {
			pt.setDouble(1, transferAmount);
			pt.setLong(2, accountID);

			int res = pt.executeUpdate();
			if (res == 1) {
				System.out.println("credited");
				return true;
			}
		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int recordFundTransfer(long fromaccount, long toaccount,
			double transferAmount) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt1 = conn
						.prepareStatement(IQueryMapper.RECORD_FUND_TRANSFER);
				PreparedStatement pt2 = conn
						.prepareStatement(IQueryMapper.GET_FUND_TRANSFER_ID);) {
			pt1.setLong(1, fromaccount);
			pt1.setLong(2, toaccount);
			pt1.setDouble(3, transferAmount);

			int result = pt1.executeUpdate();
			if (result == 1) {
				System.out.println("fund transfer updated");
				ResultSet res = pt2.executeQuery();
				if (res.next()) {
					return res.getInt(1);
				} else {
					return 0;
				}
			} else {
				return 0;
			} 
		}catch (OnlineBankingException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return 0;
		}

	@Override
	public boolean isFirstTimeUser(int userId) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.IS_NEW_USER);) {

			pt.setInt(1, userId);

			ResultSet resSet = pt.executeQuery();

			if (resSet.next()) {
				if (resSet.getString(1)==null)
					return true;
				return false;
			} else {
				return true;
			}
		} catch (OnlineBankingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public int recordTransaction(long accountId, int fundTransferId,
			String type, double transferAmount) {
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt1 = conn
						.prepareStatement(IQueryMapper.RECORD_TRANSACTION);
				PreparedStatement pt2 = conn
						.prepareStatement(IQueryMapper.GET_TRANSACTION_ID);) {
			String transDesc = ("FT:" + fundTransferId);
			pt1.setString(1, transDesc);
			pt1.setString(2, type);
			pt1.setDouble(3, transferAmount);
			pt1.setLong(4, accountId);

			int result = pt1.executeUpdate();
			if (result == 1) {
				System.out.println("Transaction update " + type);
				ResultSet res = pt2.executeQuery();
				if (res.next()) {
					return res.getInt(1);
				} else {
					return 0;
				}
			} else {
				return 0;
			}

		} catch (OnlineBankingException e) {

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void addPayee(Payee payee) throws OnlineBankingException {
		int rows = 0;
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.ADD_PAYEE);) {
			System.out.println(payee);
			pt.setLong(1, payee.getAccountId());
			pt.setLong(2, payee.getPayeeAccountId());
			pt.setString(3, payee.getNickName());
			rows = pt.executeUpdate();
		} catch (OnlineBankingException | SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getUserTransPassword(long userId) {
		String pass=null;
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.GET_TRANSACTION_PASSWORD);) {
			
			pt.setLong(1,userId);
			
			ResultSet resultset = pt.executeQuery();
			if(resultset.next()){
				pass=resultset.getString(1);
				
			}
		} catch (OnlineBankingException | SQLException e) {
			e.printStackTrace();
		} 
		return pass;

	}

	@Override
	public void completeProfile(ArrayList<String> userData, int userId) throws OnlineBankingException{
		try (Connection conn = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement pt = conn
						.prepareStatement(IQueryMapper.COMPLETE_USER_PROFILE);) {

			pt.setString(1, userData.get(1));
			pt.setString(2, userData.get(2));
			pt.setString(3, userData.get(3));
			pt.setInt(4, userId);
			
			pt.executeUpdate();
		} catch (OnlineBankingException e) {
			throw new OnlineBankingException(Messages.UPDATE_FAILED);
		} catch (SQLException e) {
			throw new OnlineBankingException(Messages.UPDATE_FAILED);
		}

	}
}
