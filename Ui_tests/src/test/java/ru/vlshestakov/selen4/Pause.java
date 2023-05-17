package ru.vlshestakov.selen4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pause {
	private static final Logger log = LogManager.getLogger();


	private static String prev="";

    /**
     * Приостанавливает работу потока на указанное количество милисекунд. Не бросает {@link InterruptedException}.
     * Если отлавливает InterruptedException, то бросается {@link AssertionError}
     * @param millis количество милисекунд, на которое необходимо сделать паузу
     * @param msg сообщение
     */
	public static void pause(long millis, String msg) {
		try {
			if (msg != null && !msg.equals(prev)) {
				prev = msg;
				log.info("Пауза '{}':{} ms...", msg, millis);
			} else {
				log.info("Пауза {} ms...", millis);
			}
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new AssertionError("Поток прерван при ожидании.", e);
		}
	}

    public static void pause(long millis) {
        pause(millis, null);
    }

    public static void ms(long millis) {
        pause(millis);
    }

    public static void seconds(long sec) {
        pause(sec * 1000L);
    }

    public static void ms(long millis, String msg) {
        pause(millis, msg);
    }

    public static void seconds(long sec, String msg) {
        pause(sec * 1000L, msg);
    }

    public static void minutes(long min) {
    	seconds(min * 60);
    }
}
