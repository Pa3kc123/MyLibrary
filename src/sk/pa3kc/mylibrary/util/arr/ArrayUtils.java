package sk.pa3kc.mylibrary.util.arr;

import sk.pa3kc.mylibrary.enums.ClassCode;
import sk.pa3kc.mylibrary.pojo.ObjectPointer;

public class ArrayUtils
{
    private static ArrayUtils instance = null;

    private BoolTypeUtils boolType = null;
    private ByteTypeUtils byteType = null;
    private CharTypeUtils charType = null;
    private DoubleTypeUtils doubleType = null;
    private FloatTypeUtils floatType = null;
    private IntTypeUtils intType = null;
    private LongTypeUtils longType = null;
    private ObjectTypeUtils objType = null;
    private ShortTypeUtils shortType = null;

    private ArrayUtils() {}

    static ArrayUtils getInst()
    {
        if (instance == null)
            instance = new ArrayUtils();
        return instance;
    }

    BoolTypeUtils getBoolType()
    {
        if (this.boolType == null)
            this.boolType = new BoolTypeUtils();
        return this.boolType;
    }
    ByteTypeUtils getByteType()
    {
        if (this.byteType == null)
            this.byteType = new ByteTypeUtils();
        return this.byteType;
    }
    CharTypeUtils getCharType()
    {
        if (this.charType == null)
            this.charType = new CharTypeUtils();
        return this.charType;
    }
    DoubleTypeUtils getDoubleType()
    {
        if (this.doubleType == null)
            this.doubleType = new DoubleTypeUtils();
        return this.doubleType;
    }
    FloatTypeUtils getFloatType()
    {
        if (this.floatType == null)
            this.floatType = new FloatTypeUtils();
        return this.floatType;
    }
    IntTypeUtils getIntType()
    {
        if (this.intType == null)
            this.intType = new IntTypeUtils();
        return this.intType;
    }
    LongTypeUtils getLongType()
    {
        if (this.longType == null)
            this.longType = new LongTypeUtils();
        return this.longType;
    }
    ObjectTypeUtils getObjType()
    {
        if (this.objType == null)
            this.objType = new ObjectTypeUtils();
        return this.objType;
    }
    ShortTypeUtils getShortType()
    {
        if (this.shortType == null)
            this.shortType = new ShortTypeUtils();
        return this.shortType;
    }

    public static <T> boolean add(T[] type, ObjectPointer<T[]> pointer, T value)
    {
        if (type == null) throw new NullPointerException("type cannot be null");
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");

        if (pointer.value.getClass().isArray() == false) throw new IllegalArgumentException("value inside of pointer must be array type");

        Class<Object> obj = Object.class;

        if (type.getClass().getComponentType() == boolean.class) return ArrayUtils.getInst().getBoolType().add(pointer.as(boolean[].class), ((Boolean)value).booleanValue());
        if (type.getClass().getComponentType() == byte.class) return ArrayUtils.getInst().getByteType().add(pointer.as(byte[].class), ((Byte)value).byteValue());
        if (type.getClass().getComponentType() == char.class) return ArrayUtils.getInst().getCharType().add(pointer.as(char[].class), ((Character)value).charValue());
        if (type.getClass().getComponentType() == short.class) return ArrayUtils.getInst().getShortType().add(pointer.as(short[].class), ((Short)value).shortValue());
        if (type.getClass().getComponentType() == int.class) return ArrayUtils.getInst().getIntType().add(pointer.as(int[].class), ((Integer)value).intValue());
        if (type.getClass().getComponentType() == long.class) return ArrayUtils.getInst().getLongType().add(pointer.as(long[].class), ((Long)value).longValue());
        if (type.getClass().getComponentType() == float.class) return ArrayUtils.getInst().getFloatType().add(pointer.as(float[].class), ((Float)value).floatValue());
        if (type.getClass().getComponentType() == double.class) return ArrayUtils.getInst().getDoubleType().add(pointer.as(double[].class), ((Double)value).doubleValue());
        return ArrayUtils.getInst().getObjType().add(obj, pointer.as(Object[].class), value);
    }

    public static int find(ClassCode type, Object arr, Object value)
    {
        if (type == null) throw new NullPointerException("type cannot be null");
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");

        if (arr.getClass().isArray() == false) throw new IllegalArgumentException("arr must be array type");

        Class<Object> obj = Object.class;
        switch (type)
        {
            case BOOLEAN: return ArrayUtils.getInst().getBoolType().find((boolean[])arr, ((Boolean)value).booleanValue());
            case BYTE: return ArrayUtils.getInst().getByteType().find((byte[])arr, ((Byte)value).byteValue());
            case CHAR: return ArrayUtils.getInst().getCharType().find((char[])arr, ((Character)value).charValue());
            case SHORT: return ArrayUtils.getInst().getShortType().find((short[])arr, ((Short)value).shortValue());
            case INT: return ArrayUtils.getInst().getIntType().find((int[])arr, ((Integer)value).intValue());
            case LONG: return ArrayUtils.getInst().getLongType().find((long[])arr, ((Long)value).longValue());
            case FLOAT: return ArrayUtils.getInst().getFloatType().find((float[])arr, ((Float)value).floatValue());
            case DOUBLE: return ArrayUtils.getInst().getDoubleType().find((double[])arr, ((Double)value).doubleValue());
            case OBJECT: return ArrayUtils.getInst().getObjType().find(obj, (Object[])arr, value);
            default: return -1;
        }
    }

    public static Boolean[] wrap(boolean[] arr) { return ArrayUtils.getInst().getBoolType().wrap(arr); }
    public static Byte[] wrap(byte[] arr) { return ArrayUtils.getInst().getByteType().wrap(arr); }
    public static Character[] wrap(char[] arr) { return ArrayUtils.getInst().getCharType().wrap(arr); }
    public static Short[] wrap(short[] arr) { return ArrayUtils.getInst().getShortType().wrap(arr); }
    public static Integer[] wrap(int[] arr) { return ArrayUtils.getInst().getIntType().wrap(arr); }
    public static Long[] wrap(long[] arr) { return ArrayUtils.getInst().getLongType().wrap(arr); }
    public static Float[] wrap(float[] arr) { return ArrayUtils.getInst().getFloatType().wrap(arr); }
    public static Double[] wrap(double[] arr) { return ArrayUtils.getInst().getDoubleType().wrap(arr); }

    public static boolean[] unwrap(Boolean[] arr) { return ArrayUtils.getInst().getBoolType().unwrap(arr); }
    public static byte[] unwrap(Byte[] arr) { return ArrayUtils.getInst().getByteType().unwrap(arr); }
    public static char[] unwrap(Character[] arr) { return ArrayUtils.getInst().getCharType().unwrap(arr); }
    public static short[] unwrap(Short[] arr) { return ArrayUtils.getInst().getShortType().unwrap(arr); }
    public static int[] unwrap(Integer[] arr) { return ArrayUtils.getInst().getIntType().unwrap(arr); }
    public static long[] unwrap(Long[] arr) { return ArrayUtils.getInst().getLongType().unwrap(arr); }
    public static float[] unwrap(Float[] arr) { return ArrayUtils.getInst().getFloatType().unwrap(arr); }
    public static double[] unwrap(Double[] arr) { return ArrayUtils.getInst().getDoubleType().unwrap(arr); }
}
