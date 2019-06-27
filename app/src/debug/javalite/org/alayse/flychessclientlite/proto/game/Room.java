// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: room.proto

package org.alayse.flychessclientlite.proto.game;

public final class Room {
  private Room() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface RoomRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:org.alayse.flychessclientlite.proto.game.RoomRequest)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>required string room = 1;</code>
     */
    boolean hasRoom();
    /**
     * <code>required string room = 1;</code>
     */
    java.lang.String getRoom();
    /**
     * <code>required string room = 1;</code>
     */
    com.google.protobuf.ByteString
        getRoomBytes();
  }
  /**
   * Protobuf type {@code org.alayse.flychessclientlite.proto.game.RoomRequest}
   */
  public  static final class RoomRequest extends
      com.google.protobuf.GeneratedMessageLite<
          RoomRequest, RoomRequest.Builder> implements
      // @@protoc_insertion_point(message_implements:org.alayse.flychessclientlite.proto.game.RoomRequest)
      RoomRequestOrBuilder {
    private RoomRequest() {
      room_ = "";
    }
    private int bitField0_;
    public static final int ROOM_FIELD_NUMBER = 1;
    private java.lang.String room_;
    /**
     * <code>required string room = 1;</code>
     */
    public boolean hasRoom() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string room = 1;</code>
     */
    public java.lang.String getRoom() {
      return room_;
    }
    /**
     * <code>required string room = 1;</code>
     */
    public com.google.protobuf.ByteString
        getRoomBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(room_);
    }
    /**
     * <code>required string room = 1;</code>
     */
    private void setRoom(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
      room_ = value;
    }
    /**
     * <code>required string room = 1;</code>
     */
    private void clearRoom() {
      bitField0_ = (bitField0_ & ~0x00000001);
      room_ = getDefaultInstance().getRoom();
    }
    /**
     * <code>required string room = 1;</code>
     */
    private void setRoomBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
      room_ = value.toStringUtf8();
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeString(1, getRoom());
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(1, getRoom());
      }
      size += unknownFields.getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static org.alayse.flychessclientlite.proto.game.Room.RoomRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(org.alayse.flychessclientlite.proto.game.Room.RoomRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    /**
     * Protobuf type {@code org.alayse.flychessclientlite.proto.game.RoomRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          org.alayse.flychessclientlite.proto.game.Room.RoomRequest, Builder> implements
        // @@protoc_insertion_point(builder_implements:org.alayse.flychessclientlite.proto.game.RoomRequest)
        org.alayse.flychessclientlite.proto.game.Room.RoomRequestOrBuilder {
      // Construct using org.alayse.flychessclientlite.proto.game.Room.RoomRequest.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>required string room = 1;</code>
       */
      public boolean hasRoom() {
        return instance.hasRoom();
      }
      /**
       * <code>required string room = 1;</code>
       */
      public java.lang.String getRoom() {
        return instance.getRoom();
      }
      /**
       * <code>required string room = 1;</code>
       */
      public com.google.protobuf.ByteString
          getRoomBytes() {
        return instance.getRoomBytes();
      }
      /**
       * <code>required string room = 1;</code>
       */
      public Builder setRoom(
          java.lang.String value) {
        copyOnWrite();
        instance.setRoom(value);
        return this;
      }
      /**
       * <code>required string room = 1;</code>
       */
      public Builder clearRoom() {
        copyOnWrite();
        instance.clearRoom();
        return this;
      }
      /**
       * <code>required string room = 1;</code>
       */
      public Builder setRoomBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setRoomBytes(value);
        return this;
      }

      // @@protoc_insertion_point(builder_scope:org.alayse.flychessclientlite.proto.game.RoomRequest)
    }
    private byte memoizedIsInitialized = -1;
    protected final Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        Object arg0, Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new org.alayse.flychessclientlite.proto.game.Room.RoomRequest();
        }
        case IS_INITIALIZED: {
          byte isInitialized = memoizedIsInitialized;
          if (isInitialized == 1) return DEFAULT_INSTANCE;
          if (isInitialized == 0) return null;

          boolean shouldMemoize = ((Boolean) arg0).booleanValue();
          if (!hasRoom()) {
            if (shouldMemoize) {
              memoizedIsInitialized = 0;
            }
            return null;
          }
          if (shouldMemoize) memoizedIsInitialized = 1;
          return DEFAULT_INSTANCE;

        }
        case MAKE_IMMUTABLE: {
          return null;
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case VISIT: {
          Visitor visitor = (Visitor) arg0;
          org.alayse.flychessclientlite.proto.game.Room.RoomRequest other = (org.alayse.flychessclientlite.proto.game.Room.RoomRequest) arg1;
          room_ = visitor.visitString(
              hasRoom(), room_,
              other.hasRoom(), other.room_);
          if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
              .INSTANCE) {
            bitField0_ |= other.bitField0_;
          }
          return this;
        }
        case MERGE_FROM_STREAM: {
          com.google.protobuf.CodedInputStream input =
              (com.google.protobuf.CodedInputStream) arg0;
          com.google.protobuf.ExtensionRegistryLite extensionRegistry =
              (com.google.protobuf.ExtensionRegistryLite) arg1;
          try {
            boolean done = false;
            while (!done) {
              int tag = input.readTag();
              switch (tag) {
                case 0:
                  done = true;
                  break;
                default: {
                  if (!parseUnknownField(tag, input)) {
                    done = true;
                  }
                  break;
                }
                case 10: {
                  String s = input.readString();
                  bitField0_ |= 0x00000001;
                  room_ = s;
                  break;
                }
              }
            }
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw new RuntimeException(e.setUnfinishedMessage(this));
          } catch (java.io.IOException e) {
            throw new RuntimeException(
                new com.google.protobuf.InvalidProtocolBufferException(
                    e.getMessage()).setUnfinishedMessage(this));
          } finally {
          }
        }
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          if (PARSER == null) {    synchronized (org.alayse.flychessclientlite.proto.game.Room.RoomRequest.class) {
              if (PARSER == null) {
                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
              }
            }
          }
          return PARSER;
        }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:org.alayse.flychessclientlite.proto.game.RoomRequest)
    private static final org.alayse.flychessclientlite.proto.game.Room.RoomRequest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new RoomRequest();
      DEFAULT_INSTANCE.makeImmutable();
    }

    public static org.alayse.flychessclientlite.proto.game.Room.RoomRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<RoomRequest> PARSER;

    public static com.google.protobuf.Parser<RoomRequest> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }

  public interface RoomResponseOrBuilder extends
      // @@protoc_insertion_point(interface_extends:org.alayse.flychessclientlite.proto.game.RoomResponse)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>required int32 err_code = 1;</code>
     */
    boolean hasErrCode();
    /**
     * <code>required int32 err_code = 1;</code>
     */
    int getErrCode();

    /**
     * <code>required string err_msg = 2;</code>
     */
    boolean hasErrMsg();
    /**
     * <code>required string err_msg = 2;</code>
     */
    java.lang.String getErrMsg();
    /**
     * <code>required string err_msg = 2;</code>
     */
    com.google.protobuf.ByteString
        getErrMsgBytes();
  }
  /**
   * Protobuf type {@code org.alayse.flychessclientlite.proto.game.RoomResponse}
   */
  public  static final class RoomResponse extends
      com.google.protobuf.GeneratedMessageLite<
          RoomResponse, RoomResponse.Builder> implements
      // @@protoc_insertion_point(message_implements:org.alayse.flychessclientlite.proto.game.RoomResponse)
      RoomResponseOrBuilder {
    private RoomResponse() {
      errMsg_ = "";
    }
    /**
     * Protobuf enum {@code org.alayse.flychessclientlite.proto.game.RoomResponse.Error}
     */
    public enum Error
        implements com.google.protobuf.Internal.EnumLite {
      /**
       * <code>ERR_OK = 0;</code>
       */
      ERR_OK(0),
      /**
       * <code>ERR_SYS = -1;</code>
       */
      ERR_SYS(-1),
      ;

      /**
       * <code>ERR_OK = 0;</code>
       */
      public static final int ERR_OK_VALUE = 0;
      /**
       * <code>ERR_SYS = -1;</code>
       */
      public static final int ERR_SYS_VALUE = -1;


      public final int getNumber() {
        return value;
      }

      /**
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static Error valueOf(int value) {
        return forNumber(value);
      }

      public static Error forNumber(int value) {
        switch (value) {
          case 0: return ERR_OK;
          case -1: return ERR_SYS;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<Error>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          Error> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<Error>() {
              public Error findValueByNumber(int number) {
                return Error.forNumber(number);
              }
            };

      private final int value;

      private Error(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:org.alayse.flychessclientlite.proto.game.RoomResponse.Error)
    }

    private int bitField0_;
    public static final int ERR_CODE_FIELD_NUMBER = 1;
    private int errCode_;
    /**
     * <code>required int32 err_code = 1;</code>
     */
    public boolean hasErrCode() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required int32 err_code = 1;</code>
     */
    public int getErrCode() {
      return errCode_;
    }
    /**
     * <code>required int32 err_code = 1;</code>
     */
    private void setErrCode(int value) {
      bitField0_ |= 0x00000001;
      errCode_ = value;
    }
    /**
     * <code>required int32 err_code = 1;</code>
     */
    private void clearErrCode() {
      bitField0_ = (bitField0_ & ~0x00000001);
      errCode_ = 0;
    }

    public static final int ERR_MSG_FIELD_NUMBER = 2;
    private java.lang.String errMsg_;
    /**
     * <code>required string err_msg = 2;</code>
     */
    public boolean hasErrMsg() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required string err_msg = 2;</code>
     */
    public java.lang.String getErrMsg() {
      return errMsg_;
    }
    /**
     * <code>required string err_msg = 2;</code>
     */
    public com.google.protobuf.ByteString
        getErrMsgBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(errMsg_);
    }
    /**
     * <code>required string err_msg = 2;</code>
     */
    private void setErrMsg(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
      errMsg_ = value;
    }
    /**
     * <code>required string err_msg = 2;</code>
     */
    private void clearErrMsg() {
      bitField0_ = (bitField0_ & ~0x00000002);
      errMsg_ = getDefaultInstance().getErrMsg();
    }
    /**
     * <code>required string err_msg = 2;</code>
     */
    private void setErrMsgBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
      errMsg_ = value.toStringUtf8();
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(1, errCode_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeString(2, getErrMsg());
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, errCode_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getErrMsg());
      }
      size += unknownFields.getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static org.alayse.flychessclientlite.proto.game.Room.RoomResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static org.alayse.flychessclientlite.proto.game.Room.RoomResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(org.alayse.flychessclientlite.proto.game.Room.RoomResponse prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    /**
     * Protobuf type {@code org.alayse.flychessclientlite.proto.game.RoomResponse}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          org.alayse.flychessclientlite.proto.game.Room.RoomResponse, Builder> implements
        // @@protoc_insertion_point(builder_implements:org.alayse.flychessclientlite.proto.game.RoomResponse)
        org.alayse.flychessclientlite.proto.game.Room.RoomResponseOrBuilder {
      // Construct using org.alayse.flychessclientlite.proto.game.Room.RoomResponse.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>required int32 err_code = 1;</code>
       */
      public boolean hasErrCode() {
        return instance.hasErrCode();
      }
      /**
       * <code>required int32 err_code = 1;</code>
       */
      public int getErrCode() {
        return instance.getErrCode();
      }
      /**
       * <code>required int32 err_code = 1;</code>
       */
      public Builder setErrCode(int value) {
        copyOnWrite();
        instance.setErrCode(value);
        return this;
      }
      /**
       * <code>required int32 err_code = 1;</code>
       */
      public Builder clearErrCode() {
        copyOnWrite();
        instance.clearErrCode();
        return this;
      }

      /**
       * <code>required string err_msg = 2;</code>
       */
      public boolean hasErrMsg() {
        return instance.hasErrMsg();
      }
      /**
       * <code>required string err_msg = 2;</code>
       */
      public java.lang.String getErrMsg() {
        return instance.getErrMsg();
      }
      /**
       * <code>required string err_msg = 2;</code>
       */
      public com.google.protobuf.ByteString
          getErrMsgBytes() {
        return instance.getErrMsgBytes();
      }
      /**
       * <code>required string err_msg = 2;</code>
       */
      public Builder setErrMsg(
          java.lang.String value) {
        copyOnWrite();
        instance.setErrMsg(value);
        return this;
      }
      /**
       * <code>required string err_msg = 2;</code>
       */
      public Builder clearErrMsg() {
        copyOnWrite();
        instance.clearErrMsg();
        return this;
      }
      /**
       * <code>required string err_msg = 2;</code>
       */
      public Builder setErrMsgBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setErrMsgBytes(value);
        return this;
      }

      // @@protoc_insertion_point(builder_scope:org.alayse.flychessclientlite.proto.game.RoomResponse)
    }
    private byte memoizedIsInitialized = -1;
    protected final Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        Object arg0, Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new org.alayse.flychessclientlite.proto.game.Room.RoomResponse();
        }
        case IS_INITIALIZED: {
          byte isInitialized = memoizedIsInitialized;
          if (isInitialized == 1) return DEFAULT_INSTANCE;
          if (isInitialized == 0) return null;

          boolean shouldMemoize = ((Boolean) arg0).booleanValue();
          if (!hasErrCode()) {
            if (shouldMemoize) {
              memoizedIsInitialized = 0;
            }
            return null;
          }
          if (!hasErrMsg()) {
            if (shouldMemoize) {
              memoizedIsInitialized = 0;
            }
            return null;
          }
          if (shouldMemoize) memoizedIsInitialized = 1;
          return DEFAULT_INSTANCE;

        }
        case MAKE_IMMUTABLE: {
          return null;
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case VISIT: {
          Visitor visitor = (Visitor) arg0;
          org.alayse.flychessclientlite.proto.game.Room.RoomResponse other = (org.alayse.flychessclientlite.proto.game.Room.RoomResponse) arg1;
          errCode_ = visitor.visitInt(
              hasErrCode(), errCode_,
              other.hasErrCode(), other.errCode_);
          errMsg_ = visitor.visitString(
              hasErrMsg(), errMsg_,
              other.hasErrMsg(), other.errMsg_);
          if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
              .INSTANCE) {
            bitField0_ |= other.bitField0_;
          }
          return this;
        }
        case MERGE_FROM_STREAM: {
          com.google.protobuf.CodedInputStream input =
              (com.google.protobuf.CodedInputStream) arg0;
          com.google.protobuf.ExtensionRegistryLite extensionRegistry =
              (com.google.protobuf.ExtensionRegistryLite) arg1;
          try {
            boolean done = false;
            while (!done) {
              int tag = input.readTag();
              switch (tag) {
                case 0:
                  done = true;
                  break;
                default: {
                  if (!parseUnknownField(tag, input)) {
                    done = true;
                  }
                  break;
                }
                case 8: {
                  bitField0_ |= 0x00000001;
                  errCode_ = input.readInt32();
                  break;
                }
                case 18: {
                  String s = input.readString();
                  bitField0_ |= 0x00000002;
                  errMsg_ = s;
                  break;
                }
              }
            }
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw new RuntimeException(e.setUnfinishedMessage(this));
          } catch (java.io.IOException e) {
            throw new RuntimeException(
                new com.google.protobuf.InvalidProtocolBufferException(
                    e.getMessage()).setUnfinishedMessage(this));
          } finally {
          }
        }
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          if (PARSER == null) {    synchronized (org.alayse.flychessclientlite.proto.game.Room.RoomResponse.class) {
              if (PARSER == null) {
                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
              }
            }
          }
          return PARSER;
        }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:org.alayse.flychessclientlite.proto.game.RoomResponse)
    private static final org.alayse.flychessclientlite.proto.game.Room.RoomResponse DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new RoomResponse();
      DEFAULT_INSTANCE.makeImmutable();
    }

    public static org.alayse.flychessclientlite.proto.game.Room.RoomResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<RoomResponse> PARSER;

    public static com.google.protobuf.Parser<RoomResponse> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}
