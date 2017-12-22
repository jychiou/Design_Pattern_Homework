package com.senao.designpattern.observer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 目標類別
 * 
 * 提供了觀察者欲追蹤的狀態。也利用所提供的方法,來通知所有的觀察者其狀態已經更新。此類別擁有以下函式
 * 		取得狀態(GetState)：回傳該目標物件的狀態。
 *
 * @author 014616
 *
 */
public class Clock implements Runnable  {
	
	public static final String SUBJECT_SECOND = "second";	// 每秒
	
	public static final String SUBJECT_MINUTE = "minute";	// 每分鐘
	
	public static final String SUBJECT_PUNCTUALLY = "punctually";	// 整點
	
	// 主題 -> 觀察者清單
	private Map<String, List<IObserver>> observerMap = new Hashtable<String, List<IObserver>>();
	
	/**
	 * 添附(Attach)：新增觀察者到串鍊內，以追蹤目標物件的變化
	 * 
	 * @param subject 主題
	 * @param observer 觀察者
	 */
	public void register(String subject, IObserver observer) {
		List<IObserver> observers = observerMap.get(subject);
		
		if(observers==null) {
			observers = new ArrayList<IObserver>();
			observerMap.put(subject, observers);
		}
		
		if(!observers.contains(observer))
			observers.add(observer);
	}
	
	/**
	 * 解附(Detach)：將已經存在的觀察者從串鍊中移除。
	 * 
	 * @param subject 主題
	 * @param observer 觀察者
	 */
	public void deRegister(String subject, IObserver observer) {
		List<IObserver> observers = observerMap.get(subject);
		
		if(observers==null)
			return;
		
		if (observers.contains(observer))
			observers.remove(observer);
	}
	
	/**
	 * 通知(Notify)：利用觀察者所提供的更新函式來通知此目標已經產生變化。
	 * 
	 * @param subject 主題
	 * @param hours 時
	 * @param minutes 分
	 * @param seconds 秒
	 */
	private void notifyObserver(String subject, int hours, int minutes, int seconds) {
		List<IObserver> observers = observerMap.get(subject);
		
		if(observers==null)
			return;
		
		for(IObserver observer : observers) {
			observer.update(subject, hours, minutes, seconds);
		}
	}
	
	/**
	 * 通知(Notify)：利用觀察者所提供的更新函式來通知此目標已經產生變化。
	 */
	private void onTick() {
		Calendar rightNow = Calendar.getInstance();
		
		int hours = rightNow.get(Calendar.HOUR_OF_DAY);
		int minutes = rightNow.get(Calendar.MINUTE);
		int seconds = rightNow.get(Calendar.SECOND);

		// 每秒
		notifyObserver(SUBJECT_SECOND, hours, minutes, seconds);
		
		if(seconds==0) {
			// 每分鐘
			notifyObserver(SUBJECT_MINUTE, hours, minutes, seconds);
	
			if(minutes==0)
				// 整點
				notifyObserver(SUBJECT_PUNCTUALLY, hours, minutes, seconds);
		}
	}
	
	@Override
	public void run() {
		
		for(;;) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			// 每一秒鐘會執行 onTick 一次
			onTick();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// 目標類別
		Clock clock = new Clock();
		
		// 觀察者類別
		IObserver digitalClock = new DigitalClock();
		
		// 新增觀察者目標物件(主題：每秒)
		clock.register(SUBJECT_SECOND, digitalClock);
		
		// 新增觀察者目標物件(主題：每分鐘)
		clock.register(SUBJECT_MINUTE, digitalClock);		
		
		// 新增觀察者目標物件(主題：整點)
		clock.register(SUBJECT_PUNCTUALLY, digitalClock);
		
		Thread t = new Thread(clock);
        t.start();
	}
}
