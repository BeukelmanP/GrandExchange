/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

/**
 *
 * @author SwekLord420
 */
public class DatabaseListener extends Observable {

    private java.sql.Connection myConn = null;
    ArrayList<Integer> AuctionIdList;
    private ResultSet myRs = null;
    private boolean newAuctions = false;
    Listener listen;

    public DatabaseListener() {
        this.AuctionIdList = new ArrayList<>();
        if (getConnection()) {
            try {
                listen = new Listener(myConn);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                System.out.println("Can't create database listener Object.");
            }
        }
        listen.start();
    }

    private void updateList() {
        ResultSet tempResultSet;
        java.sql.Connection tempCon = this.myConn;
        CallableStatement tempStatement;

        try {
            tempStatement = tempCon.prepareCall("{call get_updated_auction_ids()}");
            tempStatement.execute();
            tempResultSet = tempStatement.getResultSet();
            if (tempResultSet != null){
            while (tempResultSet.next()) {
                AuctionIdList.add(tempResultSet.getInt("auctionID"));
                System.out.println(tempResultSet.getInt("auctionID"));
            }
            }
            else{
                System.out.println("resultSet of NEW auction id's is empty ?!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private ArrayList<Integer> getUpdateList(){
        return this.AuctionIdList;
    }

    public boolean getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection("jdbc:mysql://vserver213.axc.nl:3306/lesleya213_pts?noAccessToProcedureBodies=true", "lesleya213_pts", "wachtwoord123");
            System.out.println("Database Listener connection succesfully started...");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Listener failed to start connection to database...");
            return false;
        }
    }

    class Listener extends Thread {

        private java.sql.Connection conn;
        CallableStatement myStmt;

        Listener(java.sql.Connection conn) throws SQLException {

            this.conn = conn;
            this.myStmt = conn.prepareCall("{call get_count(?)}");
            myStmt.registerOutParameter(1, Types.INTEGER);

        }

        @Override
        public void run() {
            while (true) {
                boolean execute = true;
                try {

                    if(execute == myStmt.execute()){
                        System.out.println("resultSet Found !");
                    }

                    if (myStmt.getInt(1) >= 1) {
                        updateList();
                        setChanged();
                        notifyObservers(getUpdateList());
                    } else {
                        System.out.println(myStmt.getInt(1));
                        System.out.println("No new auctions !");
                    }

                    Thread.sleep(5000);
                } catch (Exception exc) {
                    System.out.println(exc.getMessage());
                    close(conn, myStmt);
                    System.out.println("Listener Thread interrupted.");
                }
            }
        }

        private void close(Connection myConn, Statement myStmt) {
            if (myStmt != null) {
                try {
                    myStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (myConn != null) {
                try {
                    myConn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
