package net.bxx2004.pandalib.convert;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface Convert<T> {
    public T parseObject(String s);
    public String parseString(T t);
    public boolean equal(T t,T t1);
    public boolean equal(String s,String s1);
    public class Manager{

        protected Manager(ConvertOption option,Class<? extends Convert> clazz){

        }
        public static<T> Convert<T> make(Class type,ConvertOption option){
            try {
                return (Convert<T>) type.getDeclaredConstructor(ConvertOption.class).newInstance(option);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        public static<T> Convert<T> make(Class type){
            try {
                return (Convert<T>) type.getDeclaredConstructor(ConvertOption.class).newInstance(new ConvertOption());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
