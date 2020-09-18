package aoop.asteroids.server;

import java.io.*;

public class SerializePacket {

    /**
     * Transforms the object into a byte array.
     * @param o object to be compressed.
     * @return the package in byte form.
     */
    public static byte[] serialize(Object o){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try{
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.flush();
            return bos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads the byte array into an Object.
     * @param b the package in byte form.
     * @return the Object.
     */
    public static Object deserialize(byte[] b){
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            return in.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
