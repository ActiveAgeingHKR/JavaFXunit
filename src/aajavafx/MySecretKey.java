/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;



/**
 * Created by Iuliu on 2016-12-10.
 */
public class MySecretKey {
    private static final long NUMBER1 = 9909567478476247L;
    private static final long NUMBER2 = 6247472905479567L;
    private static final long NUMBER3 = 4725259948019567L;
    private static final long NUMBER4 = 9567444454665655L;
    long a = NUMBER1;
    long b = NUMBER2;
    long c = NUMBER3;
    long d = NUMBER4;
    long temp1 = (a * c) + b;
    long temp2 = a * (b - c);
    long result3 = temp1 % b;

    long result4=temp2 % c;
    public String getNumber1() {

        long temp1 = (a * c) + b;
        long temp2 = a * (b - c);
        long result3 = temp1 % b;

        long result4=temp2 % c;
        String s=Long.toString(result3);


        return s;
    }
    public String getNumber2() {
        long a = NUMBER1;
        long b = NUMBER2;
        long c = NUMBER3;
        long d = NUMBER4;
        long temp1 = (a * c) + b;
        long temp2 = a * (b - c);
        long result4 = temp2 % b;

        String s=Long.toString(result4);
        return s;
    }
}
