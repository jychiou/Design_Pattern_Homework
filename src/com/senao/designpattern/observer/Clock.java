package com.senao.designpattern.observer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

	// 觀察者的清單
	private List<IObserver> observers = new ArrayList<IObserver>();
	
	/**
	 * 添附(Attach)：新增觀察者到串鍊內，以追蹤目標物件的變化
	 * @param observer
	 */
	public void register(IObserver observer) {
		if(!observers.contains(observer))
			observers.add(observer);
	}
	
	/**
	 * 解附(Detach)：將已經存在的觀察者從串鍊中移除。
	 * @param observer
	 */
	public void deRegister(IObserver observer) {
		if (observers.contains(observer))
			observers.remove(observer);
	}
	
	/**
	 * 通知(Notify)：利用觀察者所提供的更新函式來通知此目標已經產生變化。
	 */
	private void onTick() {
		Calendar rightNow = Calendar.getInstance();
		
		int hours = rightNow.get(Calendar.HOUR_OF_DAY);
		int minutes = rightNow.get(Calendar.MINUTE);
		int seconds = rightNow.get(Calendar.SECOND);

		for(IObserver observer : observers) {
			observer.update(hours, minutes, seconds);
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
		
		// 新增觀察者目標物件
		clock.register(digitalClock);
		
		Thread t = new Thread(clock);
        t.start();
	}
}
