package com.senao.designpattern.observer;

/**
 * 觀察者類別
 * 
 * 含有指向目標類別的參考(reference)，以接收來自目標類別的更新狀態。此類別含有以下函式
 * 		更新(Update)：是前述抽象函式的實做。當這個函式被目標物件呼叫時，觀察者物件將會呼叫目標物件的取得狀態函式，來其所擁有的更新目標物件資訊。
 *
 * 每個觀察者類別都要實做它自己的更新函式，以應對狀態更新的情形。
 *
 * @author 014616
 *
 */
public class DigitalClock implements IObserver {

	@Override
	public void update(String subject, int hours, int minutes, int seconds) {
		
		if(subject.equals(Clock.SUBJECT_SECOND))
			System.out.println("現在時間:" + hours + "點 " + minutes + "分 " + seconds + "秒");
		
		else if(subject.equals(Clock.SUBJECT_MINUTE))
			System.out.println("現在時間:" + hours + "點 " + minutes + "分整 ");
		
		else if(subject.equals(Clock.SUBJECT_PUNCTUALLY))
			System.out.println("現在時間:" + hours + "點整 ");
	}

}
