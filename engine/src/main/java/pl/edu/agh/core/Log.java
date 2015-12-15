/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.core;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;

/**
 * @author uriel
 */
public class Log {
    public static Message message(String format, Object... args) {
        return new ParameterizedMessage(format, args);
    }

    public static Message message(String format, Throwable th, Object... args) {
        return new ParameterizedMessage(format, args, th);
    }
}
