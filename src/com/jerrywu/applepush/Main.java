package com.jerrywu.applepush;

import java.util.ArrayList;
import java.util.List;
import javapns.notification.AppleNotificationServer;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.transmission.NotificationProgressListener;
import javapns.notification.transmission.NotificationThread;
import javapns.notification.transmission.NotificationThreads;


public class Main {

	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("RG");
		
		String keystore = "/Users/Jerry/Desktop/Home.p12";// 證書路徑
		String password = "guh98200"; // 證書密碼
		String token = "e8024e30c218bdc1478a017c275560b0a5030c53bedd2a7eaafcacb0d7d2f5f5";// 手機唯一標識
		
		
		
		
		boolean production = false; // 設置true为正式服務地址，false为開發者地址
		int threadThreads = 5; // 線程數
		
		String msg = "想要ＧＧ了";
		
		int msgCount = 1;
		
		
		try {
			// 建立與Apple服務器連接
			AppleNotificationServer server = new AppleNotificationServerBasicImpl(
					keystore, password, production);
			List<PayloadPerDevice> list = new ArrayList<PayloadPerDevice>();
			PushNotificationPayload payload = new PushNotificationPayload();
			payload.addAlert(msg);
			payload.addSound("default");// 聲音
			payload.addBadge(msgCount);// 圖標小紅圈的數值
		
			PayloadPerDevice pay = new PayloadPerDevice(payload, token);// 將要推送的消息和手機唯一標識绑定
			list.add(pay);

			NotificationThreads work = new NotificationThreads(server, list,
					threadThreads);//
			work.setListener(DEBUGGING_PROGRESS_LISTENER);// 對線程的監聽，一定要加上這個監聽
			work.start(); // 启動線程
			work.waitForAllThreads();// 等待所有線程启動完成

		
			
			System.out.println("G2");
		} catch (Exception e) {
			System.out.println("ERROR");
			System.out.println(e.getMessage());
		}
		
		
		
		System.out.println("GG");
	}
	
	
	
	
	
	
	
	public static final NotificationProgressListener DEBUGGING_PROGRESS_LISTENER = new NotificationProgressListener() {

		public void eventThreadStarted(NotificationThread notificationThread) {
			System.out.println("A");
			System.out.println("   [EVENT]: thread #"
					+ notificationThread.getThreadNumber() + " started with "
					+ " devices beginning at message id #"
					+ notificationThread.getFirstMessageIdentifier());
		}

		public void eventThreadFinished(NotificationThread thread) {
			System.out.println("B");
			System.out.println("   [EVENT]: thread #"
					+ thread.getThreadNumber() + " finished: pushed messages #"
					+ thread.getFirstMessageIdentifier() + " to "
					+ thread.getLastMessageIdentifier() + " toward "
					+ " devices");
		}

		public void eventConnectionRestarted(NotificationThread thread) {
			System.out.println("C");
			System.out.println("   [EVENT]: connection restarted in thread #"
					+ thread.getThreadNumber() + " because it reached "
					+ thread.getMaxNotificationsPerConnection()
					+ " notifications per connection");
		}

		public void eventAllThreadsStarted(
				NotificationThreads notificationThreads) {
			System.out.println("D");
			System.out.println("   [EVENT]: all threads started: "
					+ notificationThreads.getThreads().size());
		}

		public void eventAllThreadsFinished(
				NotificationThreads notificationThreads) {
			System.out.println("E");
			System.out.println("   [EVENT]: all threads finished: "
					+ notificationThreads.getThreads().size());
		}

		public void eventCriticalException(
				NotificationThread notificationThread, Exception exception) {
			System.out.println("F");
			System.out.println("   [EVENT]: critical exception occurred: "
					+ exception);
		}
	};

}
