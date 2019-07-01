// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: messagepush.proto

package org.alayse.flychessclientlite.proto.game;

public final class Messagepush {
  private Messagepush() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface MessagePushOrBuilder extends
      // @@protoc_insertion_point(interface_extends:org.alayse.flychessclientlite.proto.game.MessagePush)
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

    /**
     * <code>required string content = 2;</code>
     */
    boolean hasContent();
    /**
     * <code>required string content = 2;</code>
     */
    java.lang.String getContent();
    /**
     * <code>required string content = 2;</code>
     */
    com.google.protobuf.ByteString
        getContentBytes();
  }
  /**
   * Protobuf type {@code org.alayse.flychessclientlite.proto.game.MessagePush}
   */
  public  static final class MessagePush extends
      com.google.protobuf.GeneratedMessageLite<
          MessagePush, MessagePush.Builder> implements
      // @@protoc_insertion_point(message_implements:org.alayse.flychessclientlite.proto.game.MessagePush)
      MessagePushOrBuilder {
    private MessagePush() {
      room_ = "";
      content_ = "";
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

    public static final int CONTENT_FIELD_NUMBER = 2;
    private java.lang.String content_;
    /**
     * <code>required string content = 2;</code>
     */
    public boolean hasContent() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required string content = 2;</code>
     */
    public java.lang.String getContent() {
      return content_;
    }
    /**
     * <code>required string content = 2;</code>
     */
    public com.google.protobuf.ByteString
        getContentBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(content_);
    }
    /**
     * <code>required string content = 2;</code>
     */
    private void setContent(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
      content_ = value;
    }
    /**
     * <code>required string content = 2;</code>
     */
    private void clearContent() {
      bitField0_ = (bitField0_ & ~0x00000002);
      content_ = getDefaultInstance().getContent();
    }
    /**
     * <code>required string content = 2;</code>
     */
    private void setContentBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
      content_ = value.toStringUtf8();
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeString(1, getRoom());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeString(2, getContent());
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
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getContent());
      }
      size += unknownFields.getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    /**
     * Protobuf type {@code org.alayse.flychessclientlite.proto.game.MessagePush}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush, Builder> implements
        // @@protoc_insertion_point(builder_implements:org.alayse.flychessclientlite.proto.game.MessagePush)
        org.alayse.flychessclientlite.proto.game.Messagepush.MessagePushOrBuilder {
      // Construct using org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush.newBuilder()
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

      /**
       * <code>required string content = 2;</code>
       */
      public boolean hasContent() {
        return instance.hasContent();
      }
      /**
       * <code>required string content = 2;</code>
       */
      public java.lang.String getContent() {
        return instance.getContent();
      }
      /**
       * <code>required string content = 2;</code>
       */
      public com.google.protobuf.ByteString
          getContentBytes() {
        return instance.getContentBytes();
      }
      /**
       * <code>required string content = 2;</code>
       */
      public Builder setContent(
          java.lang.String value) {
        copyOnWrite();
        instance.setContent(value);
        return this;
      }
      /**
       * <code>required string content = 2;</code>
       */
      public Builder clearContent() {
        copyOnWrite();
        instance.clearContent();
        return this;
      }
      /**
       * <code>required string content = 2;</code>
       */
      public Builder setContentBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setContentBytes(value);
        return this;
      }

      // @@protoc_insertion_point(builder_scope:org.alayse.flychessclientlite.proto.game.MessagePush)
    }
    private byte memoizedIsInitialized = -1;
    protected final Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        Object arg0, Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush();
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
          if (!hasContent()) {
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
          org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush other = (org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush) arg1;
          room_ = visitor.visitString(
              hasRoom(), room_,
              other.hasRoom(), other.room_);
          content_ = visitor.visitString(
              hasContent(), content_,
              other.hasContent(), other.content_);
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
                case 18: {
                  String s = input.readString();
                  bitField0_ |= 0x00000002;
                  content_ = s;
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
          if (PARSER == null) {    synchronized (org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush.class) {
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


    // @@protoc_insertion_point(class_scope:org.alayse.flychessclientlite.proto.game.MessagePush)
    private static final org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new MessagePush();
      DEFAULT_INSTANCE.makeImmutable();
    }

    public static org.alayse.flychessclientlite.proto.game.Messagepush.MessagePush getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<MessagePush> PARSER;

    public static com.google.protobuf.Parser<MessagePush> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}