/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.garethaye.minimax.generated;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinimaxConfig implements org.apache.thrift.TBase<MinimaxConfig, MinimaxConfig._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MinimaxConfig");

  private static final org.apache.thrift.protocol.TField DEPTH_FIELD_DESC = new org.apache.thrift.protocol.TField("depth", org.apache.thrift.protocol.TType.I32, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MinimaxConfigStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MinimaxConfigTupleSchemeFactory());
  }

  private int depth; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DEPTH((short)1, "depth");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // DEPTH
          return DEPTH;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __DEPTH_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DEPTH, new org.apache.thrift.meta_data.FieldMetaData("depth", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MinimaxConfig.class, metaDataMap);
  }

  public MinimaxConfig() {
  }

  public MinimaxConfig(
    int depth)
  {
    this();
    this.depth = depth;
    setDepthIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MinimaxConfig(MinimaxConfig other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.depth = other.depth;
  }

  public MinimaxConfig deepCopy() {
    return new MinimaxConfig(this);
  }

  public void clear() {
    setDepthIsSet(false);
    this.depth = 0;
  }

  public int getDepth() {
    return this.depth;
  }

  public MinimaxConfig setDepth(int depth) {
    this.depth = depth;
    setDepthIsSet(true);
    return this;
  }

  public void unsetDepth() {
    __isset_bit_vector.clear(__DEPTH_ISSET_ID);
  }

  /** Returns true if field depth is set (has been assigned a value) and false otherwise */
  public boolean isSetDepth() {
    return __isset_bit_vector.get(__DEPTH_ISSET_ID);
  }

  public void setDepthIsSet(boolean value) {
    __isset_bit_vector.set(__DEPTH_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DEPTH:
      if (value == null) {
        unsetDepth();
      } else {
        setDepth((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DEPTH:
      return Integer.valueOf(getDepth());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DEPTH:
      return isSetDepth();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MinimaxConfig)
      return this.equals((MinimaxConfig)that);
    return false;
  }

  public boolean equals(MinimaxConfig that) {
    if (that == null)
      return false;

    boolean this_present_depth = true;
    boolean that_present_depth = true;
    if (this_present_depth || that_present_depth) {
      if (!(this_present_depth && that_present_depth))
        return false;
      if (this.depth != that.depth)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();

    boolean present_depth = true;
    builder.append(present_depth);
    if (present_depth)
      builder.append(depth);

    return builder.toHashCode();
  }

  public int compareTo(MinimaxConfig other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    MinimaxConfig typedOther = (MinimaxConfig)other;

    lastComparison = Boolean.valueOf(isSetDepth()).compareTo(typedOther.isSetDepth());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDepth()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.depth, typedOther.depth);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("MinimaxConfig(");
    boolean first = true;

    sb.append("depth:");
    sb.append(this.depth);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'depth' because it's a primitive and you chose the non-beans generator.
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te.getMessage());
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te.getMessage());
    }
  }

  private static class MinimaxConfigStandardSchemeFactory implements SchemeFactory {
    public MinimaxConfigStandardScheme getScheme() {
      return new MinimaxConfigStandardScheme();
    }
  }

  private static class MinimaxConfigStandardScheme extends StandardScheme<MinimaxConfig> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MinimaxConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DEPTH
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.depth = iprot.readI32();
              struct.setDepthIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetDepth()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'depth' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, MinimaxConfig struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(DEPTH_FIELD_DESC);
      oprot.writeI32(struct.depth);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MinimaxConfigTupleSchemeFactory implements SchemeFactory {
    public MinimaxConfigTupleScheme getScheme() {
      return new MinimaxConfigTupleScheme();
    }
  }

  private static class MinimaxConfigTupleScheme extends TupleScheme<MinimaxConfig> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MinimaxConfig struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.depth);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MinimaxConfig struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.depth = iprot.readI32();
      struct.setDepthIsSet(true);
    }
  }

}
