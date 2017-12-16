package com.senao.designpattern.observer;

/**
 * 抽象觀察者介面
 * 
 * 一個必須被實做的抽象類別。這個類別定義了所有觀察者都擁有的更新用介面，此介面是用來接收目標類別所發出的更新通知。此類別含有以下函式
 * 		更新(Update)：會被實做的一個抽象(虛擬)函式。
 *
 * @author 014616
 *
 */
public interface IObserver {
	
	/**
	 * 
	 * @param hours
	 * @param minutes
	 * @param seconds
	 */
	void update(int hours, int minutes, int seconds);
}
