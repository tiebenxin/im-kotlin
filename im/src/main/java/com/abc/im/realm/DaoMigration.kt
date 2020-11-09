package com.im.db

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration
import io.realm.RealmSchema

// 1.建bean  继承 RealmObject 2.DaoMigration 写schema 升级updateVxx  3. DaoUtil 升级dbVer
class DaoMigration : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var oldVersion = oldVersion
        // DynamicRealm exposes an editable schema
        val schema = realm.schema
        if (newVersion > oldVersion) {
            if (oldVersion == 0L) {//从0升到1
                updateV1(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 1L) {//从1升到2
                updateV2(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 2L) {//从2升到3
                updateV3(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 3L) {
                updateV4(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 4L) {
                updateV5(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 5L) {
                updateV6(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 6L) {
                updateV7(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 7L) {
                updateV8(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 8L) {
                updateV9(schema)
                oldVersion++
            }

            if (newVersion > oldVersion && oldVersion == 9L) {
                updateV10(schema)
                oldVersion++
            }

            if (newVersion > oldVersion && oldVersion == 10L) {
                updateV11(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 11L) {
                updateV12(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 12L) {
                updateV13(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 13L) {
                updateV14(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 14L) {
                updateV15(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 15L) {
                updateV16(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 16L) {
                updateV17(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 17L) {
                updateV18(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 18L) {
                updateV19(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 19L) {
                updateV20(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 20L) {
                updateV21(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 21L) {
                updateV22(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 22L) {
                updateV23(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 23L) {
                updateV24(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 24L) {
                updateV25(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 25L) {
                updateV26(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 26L) {
                updateV27(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 27L) {
                updateV28(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 28L) {
                updateV29(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 29L) {
                updateV30(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 30L) {
                updateV31(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 31L) {
                updateV32(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 32L) {
                updateV33(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 33L) {
                updateV34(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 34L) {
                updateV35(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 35L) {
                updateV36(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 36L) {
                updateV37(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 37L) {
                updateV38(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 38L) {
                updateV39(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 39L) {
                updateV40(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 40L) {
                updateV41(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 41L) {
                updateV42(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 42L) {
                updateV43(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 43L) {
                updateV44(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 44L) {
                updateV45(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 45L) {
                updateV46(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 46L) {
                updateV47(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 47L) {
                updateV48(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 48L) {
                updateV49(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 49L) {
                updateV50(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 50L) {
                updateV51(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 51L) {
                updateV52(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 52L) {
                updateV53(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 53L) {
                updateV54(schema)
                oldVersion++
            }
            if (newVersion > oldVersion && oldVersion == 54L) {
                updateV55(schema)
                oldVersion++
            }
        }
    }

    /*
     * 新增群头像表
     * */
    private fun updateV1(schema: RealmSchema) {
        schema.create("GroupImageHead")
            .addField("gid", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("imgHeadUrl", String::class.java)
    }


    /*
     *UserInfo 新增字段  lockCloudRedEnvelope，destroy
     * */
    private fun updateV2(schema: RealmSchema) {
        schema.get("UserInfo")!!
            .addField("lockCloudRedEnvelope", Int::class.javaPrimitiveType)
            .addField("destroy", Int::class.javaPrimitiveType)
            .addField("destroyTime", Long::class.javaPrimitiveType)

    }

    //短视频数据库
    private fun updateV3(schema: RealmSchema) {
        schema.create("VideoMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("duration", Long::class.javaPrimitiveType)
            .addField("bg_url", String::class.java)
            .addField("width", Long::class.javaPrimitiveType)
            .addField("height", Long::class.javaPrimitiveType)
            .addField("isReadOrigin", Boolean::class.javaPrimitiveType)
            .addField("url", String::class.java)
        //                .addField("localUrl", String.class);
    }

    /*
     * 新增群头像表
     * */
    private fun updateV4(schema: RealmSchema) {
        schema.get("MsgAllBean")!!
            .addRealmObjectField("videoMessage", schema.get("VideoMessage")!!)
        schema.get("VideoMessage")!!
            .addField("localUrl", String::class.java)

    }

    private fun updateV5(schema: RealmSchema) {
        schema.get("UserInfo")!!
            .addField("joinType", Int::class.javaPrimitiveType)
            .addField("joinTime", String::class.java)
            .addField("inviter", String::class.java)
            .addField("inviterName", String::class.java)
    }

    /*
     * 1. 新建群成员表，与通讯录分离
     * 2. 更改Group中群成员存储字段名字
     * 3. 新建音视频通话表
     * setNullable，设置不能为null，也可以通过注解@Required 来实现
     * */
    private fun updateV6(schema: RealmSchema) {
        schema.create("MemberUser")
            .addField("memberId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("uid", Long::class.javaPrimitiveType)
            .addField("gid", String::class.java)/*.setNullable("gid", true)*/
            .addField("name", String::class.java)/*.setNullable("name", true)*/
            .addField("sex", Int::class.javaPrimitiveType)/*.setNullable("sex", true)*/
            .addField("imid", String::class.java)/*.setNullable("imid", true)*/
            .addField("head", String::class.java)/*.setNullable("head", true)*/
            .addField("membername", String::class.java)/*.setNullable("membername", true)*/
            .addField("joinType", Int::class.javaPrimitiveType)
            .addField("joinTime", String::class.java)
            .addField("inviter", String::class.java)/*.setNullable("inviter", true)*/
            .addField("inviterName", String::class.java)/*.setNullable("inviterName", true)*/
            .addField("tag", String::class.java)/*.setNullable("tag", true)*/
        schema.get("Group")!!
            .removeField("users")
            .addRealmListField("members", schema.get("MemberUser")!!)

        schema.create("P2PAuVideoMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("av_type", Int::class.javaPrimitiveType)
            .addField("operation", String::class.java)
            .addField("desc", String::class.java)

        schema.get("UserInfo")!!
            .addField("neteaseAccid", String::class.java)
        schema.get("MsgAllBean")!!
            .addRealmObjectField("p2PAuVideoMessage", schema.get("P2PAuVideoMessage")!!)
    }

    private fun updateV7(schema: RealmSchema) {
        schema.get("UserInfo")!!
            .addField("vip", String::class.java)
    }

    private fun updateV8(schema: RealmSchema) {
        schema.create("P2PAuVideoDialMessage")
            .addField("av_type", Int::class.javaPrimitiveType)
        schema.get("MsgAllBean")!!
            .addRealmObjectField("p2PAuVideoDialMessage", schema.get("P2PAuVideoDialMessage")!!)
    }


    private fun updateV9(schema: RealmSchema) {
        schema.get("MsgCancel")!!
            .addField("cancelContent", String::class.java)
            .addField("cancelContentType", Int::class.java)
    }


    //新增群阅后即焚
    private fun updateV10(schema: RealmSchema) {
        schema.get("Group")!!
            .addField("survivaltime", Int::class.javaPrimitiveType)

        schema.create("ChangeSurvivalTimeMessage")
            .addField("msgid", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("survival_time", Int::class.javaPrimitiveType)

        schema.get("MsgAllBean")!!
            .addField("survival_time", Int::class.javaPrimitiveType)
            .addField("serverTime", Long::class.javaPrimitiveType)
            .addField("endTime", Long::class.javaPrimitiveType)
            .addField("readTime", Long::class.javaPrimitiveType)
            .addField("startTime", Long::class.javaPrimitiveType)
            .addField("read", Int::class.javaPrimitiveType)
            .addRealmObjectField(
                "changeSurvivalTimeMessage",
                schema.get("ChangeSurvivalTimeMessage")!!
            )

        schema.get("UserInfo")!!
            .addField("masterRead", Int::class.javaPrimitiveType)
            .addField("myRead", Int::class.javaPrimitiveType)
            .addField("friendRead", Int::class.javaPrimitiveType)
    }


    //
    private fun updateV11(schema: RealmSchema) {
        schema.create("ApplyBean")
            .addField("aid", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("chatType", Int::class.javaPrimitiveType)

            .addField("uid", Long::class.javaPrimitiveType)
            .addField("nickname", String::class.java)
            .addField("alias", String::class.java)
            .addField("avatar", String::class.java)
            .addField("sayHi", String::class.java)
            .addField("stat", Int::class.javaPrimitiveType)

            .addField("gid", String::class.java)
            .addField("groupName", String::class.java)
            .addField("joinType", Int::class.javaPrimitiveType)
            .addField("inviter", Long::class.javaPrimitiveType)
            .addField("inviterName", String::class.java)
            .addField("time", Long::class.javaPrimitiveType)

        schema.get("Group")!!.addField("merchantEntry", String::class.java)
    }

    //更新红包消息
    private fun updateV12(schema: RealmSchema) {
        schema.get("RedEnvelopeMessage")!!
            .addField("traceId", Long::class.javaPrimitiveType)
            .addField("actionId", String::class.java)
    }

    //更新红包消息token
    private fun updateV13(schema: RealmSchema) {
        schema.get("RedEnvelopeMessage")!!
            .addField("accessToken", String::class.java)

    }

    //更新零钱助手及位置消息
    private fun updateV14(schema: RealmSchema) {
        schema.get("RedEnvelopeMessage")!!
            .addField("envelopStatus", Int::class.javaPrimitiveType)


        schema.create("BalanceAssistantMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("tradeId", Long::class.javaPrimitiveType)
            .addField("detailType", Int::class.javaPrimitiveType)
            .addField("time", Long::class.javaPrimitiveType)
            .addField("title", String::class.java)
            .addField("amountTitle", String::class.java)
            .addField("amount", Long::class.javaPrimitiveType)
            .addField("items", String::class.java)

        schema.create("LocationMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("latitude", Int::class.javaPrimitiveType)
            .addField("longitude", Int::class.javaPrimitiveType)
            .addField("address", String::class.java)
            .addField("addressDescribe", String::class.java)
            .addField("img", String::class.java)

        schema.get("MsgAllBean")!!
            .addRealmObjectField("balanceAssistantMessage", schema.get("BalanceAssistantMessage")!!)
            .addRealmObjectField("locationMessage", schema.get("LocationMessage")!!)


    }

    //更新红包消息token
    private fun updateV15(schema: RealmSchema) {
        schema.get("RedEnvelopeMessage")!!
            .addField("sign", String::class.java)

        schema.get("TransferMessage")!!
            .addField("sign", String::class.java)
            .addField("opType", Int::class.javaPrimitiveType)
    }

    /**
     * 更群管理, 发送失败红包临时存储表，领取转账通知表
     *
     * @param schema
     */
    private fun updateV16(schema: RealmSchema) {
        schema.get("Group")!!
            .addRealmListField("viceAdmins", Long::class.java)
            .addField("wordsNotAllowed", Int::class.java)

        schema.get("UserInfo")!!
            .addField("bankReqSignKey", String::class.java)


        schema.create("EnvelopeInfo")
            .addField("rid", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("comment", String::class.java)
            .addField("reType", Int::class.javaPrimitiveType)
            .addField("envelopeStyle", Int::class.javaPrimitiveType)
            .addField("sendStatus", Int::class.javaPrimitiveType)
            .addField("sign", String::class.java)
            .addField("createTime", Long::class.javaPrimitiveType)

        schema.create("TransferNoticeMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("rid", String::class.java)
            .addField("content", String::class.java)

        schema.get("MsgAllBean")!!
            .addRealmObjectField("transferNoticeMessage", schema.get("TransferNoticeMessage")!!)
    }

    //更新红包消息token
    private fun updateV17(schema: RealmSchema) {
        schema.get("EnvelopeInfo")!!
            .addField("gid", String::class.java)
            .addField("uid", Int::class.javaPrimitiveType)
            .addField("amount", Long::class.javaPrimitiveType)

    }

    /**
     * 添加是否能领取零钱红包
     *
     * @param schema
     */
    private fun updateV18(schema: RealmSchema) {
        schema.get("Group")!!
            .addField("cantOpenUpRedEnv", Int::class.javaPrimitiveType)
    }

    /**
     * 添加动画表情
     *
     * @param schema
     */
    private fun updateV19(schema: RealmSchema) {
        schema.create("ShippedExpressionMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("id", String::class.java)
        schema.get("MsgAllBean")!!
            .addRealmObjectField(
                "shippedExpressionMessage",
                schema.get("ShippedExpressionMessage")!!
            )

    }

    //更新截屏通知开关
    private fun updateV20(schema: RealmSchema) {
        schema.get("UserInfo")!!
            .addField("screenshotNotification", Int::class.javaPrimitiveType)//单聊截屏通知
        schema.get("Group")!!
            .addField("screenshotNotification", Int::class.javaPrimitiveType)//群聊截屏通知
    }

    //文件消息类型 新建表
    private fun updateV21(schema: RealmSchema) {
        schema.create("SendFileMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("url", String::class.java)
            .addField("file_name", String::class.java)
            .addField("format", String::class.java)
            .addField("size", Long::class.javaPrimitiveType)
            .addField("localPath", String::class.java)

        schema.get("MsgAllBean")!!
            .addRealmObjectField("sendFileMessage", schema.get("SendFileMessage")!!)
    }

    //文件消息类型 新建表
    private fun updateV22(schema: RealmSchema) {
        schema.create("WebMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("appName", String::class.java)
            .addField("title", String::class.java)
            .addField("description", String::class.java)
            .addField("webUrl", String::class.java)
            .addField("iconUrl", String::class.java)

        schema.get("MsgAllBean")!!
            .addRealmObjectField("webMessage", schema.get("WebMessage")!!)
    }

    private fun updateV23(schema: RealmSchema) {
        schema.get("SendFileMessage")!!
            .addField("isFromOther", Boolean::class.javaPrimitiveType)
    }

    private fun updateV24(schema: RealmSchema) {
        schema.get("SendFileMessage")!!
            .addField("realFileRename", String::class.java)
    }

    private fun updateV25(schema: RealmSchema) {
        schema.create("SessionDetail")
            .addField("sid", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("name", String::class.java)
            .addField("avatar", String::class.java)
            .addField("avatarList", String::class.java)
            .addField("senderName", String::class.java)
            .addRealmObjectField("message", schema.get("MsgAllBean")!!)
    }

    private fun updateV26(schema: RealmSchema) {
        schema.get("SessionDetail")!!
            .addField("messageContent", String::class.java)
    }

    private fun updateV27(schema: RealmSchema) {
        schema.get("Group")!!
            .addField("stat", Int::class.javaPrimitiveType)
    }

    private fun updateV28(schema: RealmSchema) {
        schema.get("MsgAllBean")!!
            .addField("isLocal", Int::class.javaPrimitiveType)
    }

    //新建UserBean表，将登陆账号信息单独存储，以区别文件传输助手（userId即为自己id）
    private fun updateV29(schema: RealmSchema) {
        schema.create("UserBean")
            .addField("uid", Long::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("name", String::class.java)
            .addField("mkName", String::class.java)
            .addField("sex", Int::class.javaPrimitiveType)
            .addField("imid", String::class.java)
            .addField("tag", String::class.java)
            .addField("head", String::class.java)
            .addField("uType", Int::class.java)
            .addField("phone", String::class.java)
            .addField("oldimid", String::class.java)
            .addField("neteaseAccid", String::class.java)
            .addField("vip", String::class.java)
            .addField("disturb", Int::class.java)
            .addField("istop", Int::class.java)
            .addField("phonefind", Int::class.java)
            .addField("imidfind", Int::class.java)
            .addField("friendvalid", Int::class.java)
            .addField("groupvalid", Int::class.java)
            .addField("messagenotice", Int::class.java)
            .addField("displaydetail", Int::class.java)
            .addField("stat", Int::class.java)
            .addField("authStat", Int::class.java)
            .addField("screenshotNotification", Int::class.javaPrimitiveType)
            .addField("masterRead", Int::class.javaPrimitiveType)
            .addField("myRead", Int::class.javaPrimitiveType)
            .addField("friendRead", Int::class.javaPrimitiveType)
            .addField("emptyPassword", Boolean::class.javaPrimitiveType)
            .addField("sayHi", String::class.java)
            .addField("lastonline", Long::class.java)
            .addField("activeType", Int::class.javaPrimitiveType)
            .addField("describe", String::class.java)
            .addField("lockCloudRedEnvelope", Int::class.javaPrimitiveType)
            .addField("destroy", Int::class.javaPrimitiveType)
            .addField("destroyTime", Long::class.javaPrimitiveType)
            .addField("joinType", Int::class.javaPrimitiveType)
            .addField("joinTime", String::class.java)
            .addField("inviter", String::class.java)
            .addField("inviterName", String::class.java)
            .addField("bankReqSignKey", String::class.java)
    }

    //新增单条消息回复相关表
    private fun updateV30(schema: RealmSchema) {
        schema.create("QuotedMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("timestamp", Long::class.javaPrimitiveType)
            .addField("msgType", Int::class.javaPrimitiveType)
            .addField("fromUid", Long::class.javaPrimitiveType)
            .addField("nickName", String::class.java)
            .addField("avatar", String::class.java)
            .addField("url", String::class.java)
            .addField("msg", String::class.java)

        schema.create("ReplyMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addRealmObjectField("quotedMessage", schema.get("QuotedMessage")!!)
            .addRealmObjectField("textMessage", schema.get("ChatMessage")!!)
            .addRealmObjectField("atMessage", schema.get("AtMessage")!!)

        schema.get("MsgAllBean")!!
            .addRealmObjectField("replyMessage", schema.get("ReplyMessage")!!)
    }

    //新增是否正在回复消息字段
    private fun updateV31(schema: RealmSchema) {
        schema.get("MsgAllBean")!!
            .addField("isReplying", Int::class.javaPrimitiveType)
    }

    //添加收藏相关字段，添加新的收藏消息类型
    private fun updateV32(schema: RealmSchema) {
        schema.create("CollectionInfo")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("createTime", String::class.java)
            .addField("data", String::class.java)
            .addField("fromGid", String::class.java)
            .addField("fromGroupName", String::class.java)
            .addField("fromUid", Long::class.javaPrimitiveType)
            .addField("fromUsername", String::class.java)
            .addField("id", Long::class.javaPrimitiveType)
            .addField("type", Int::class.javaPrimitiveType)
        schema.create("CollectAtMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("msg", String::class.java)
        schema.create("CollectChatMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("msg", String::class.java)
        schema.create("CollectImageMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("origin", String::class.java)
            .addField("preview", String::class.java)
            .addField("thumbnail", String::class.java)
            .addField("localimg", String::class.java)
            .addField("isReadOrigin", Boolean::class.javaPrimitiveType)
            .addField("width", Long::class.javaPrimitiveType)
            .addField("height", Long::class.javaPrimitiveType)
            .addField("size", Long::class.javaPrimitiveType)
        schema.create("CollectLocationMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("lat", Int::class.javaPrimitiveType)
            .addField("lon", Int::class.javaPrimitiveType)
            .addField("addr", String::class.java)
            .addField("addressDesc", String::class.java)
            .addField("img", String::class.java)
        schema.create("CollectSendFileMessage")  //除了ignore本地字段，都要加上
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("fileURL", String::class.java)
            .addField("fileName", String::class.java)
            .addField("fileFormat", String::class.java)
            .addField("fileSize", Long::class.javaPrimitiveType)
            .addField("collectLocalPath", String::class.java)
            .addField("collectIsFromOther", Boolean::class.javaPrimitiveType)
            .addField("collectRealFileRename", String::class.java)
        schema.create("CollectShippedExpressionMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("expression", String::class.java)
        schema.create("CollectVideoMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("videoDuration", Long::class.javaPrimitiveType)
            .addField("videoBgURL", String::class.java)
            .addField("width", Long::class.javaPrimitiveType)
            .addField("height", Long::class.javaPrimitiveType)
            .addField("size", Long::class.javaPrimitiveType)
            .addField("videoURL", String::class.java)
            .addField("isReadOrigin", Boolean::class.javaPrimitiveType)
            .addField("localUrl", String::class.java)
        schema.create("CollectVoiceMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("voiceURL", String::class.java)
            .addField("voiceDuration", Int::class.javaPrimitiveType)
            .addField("playStatus", Int::class.javaPrimitiveType)
            .addField("localUrl", String::class.java)
    }

    //新增用户全拼字段
    private fun updateV33(schema: RealmSchema) {
        schema.get("UserInfo")!!
            .addField("pinyin", String::class.java)
    }

    //新增小助手广告消息
    private fun updateV34(schema: RealmSchema) {
        schema.create("AdMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("title", String::class.java)
            .addField("summary", String::class.java)
            .addField("thumbnail", String::class.java)
            .addField("buttonTxt", String::class.java)
            .addField("appId", String::class.java)
            .addField("webUrl", String::class.java)
            .addField("schemeUrl", String::class.java)

        schema.get("MsgAllBean")!!
            .addRealmObjectField("adMessage", schema.get("AdMessage")!!)
    }

    //新增收藏操作表、收藏删除操作表，用于支持离线收藏功能
    private fun updateV35(schema: RealmSchema) {
        schema.create("OfflineCollect")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addRealmObjectField("collectionInfo", schema.get("CollectionInfo")!!)
        schema.create("OfflineDelete")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
    }

    //用户信息页面增加封锁功能
    private fun updateV36(schema: RealmSchema) {
        schema.get("UserBean")!!
            .addField("lockedFunctions", Int::class.javaPrimitiveType)
        schema.get("UserInfo")!!
            .addField("lockedFunctions", Int::class.javaPrimitiveType)
    }

    //session新增标记已读未读字段
    private fun updateV37(schema: RealmSchema) {
        schema.get("Session")!!
            .addField("markRead", Int::class.javaPrimitiveType)
    }

    //用户信息页面增加简拼字段，转账消息增加操作creator字段
    private fun updateV38(schema: RealmSchema) {
        schema.get("UserInfo")!!
            .addField("pinyinHead", String::class.java)
        schema.get("TransferMessage")!!
            .addField("creator", Long::class.javaPrimitiveType)
    }

    //增加转账消息被动关系字段
    private fun updateV39(schema: RealmSchema) {
        schema.get("TransferMessage")!!
            .addField("passive", Int::class.javaPrimitiveType)
    }

    //新增红包消息备份表
    private fun updateV40(schema: RealmSchema) {
        schema.get("UserInfo")!!
            .addField("lockedstatus", Int::class.javaPrimitiveType)

        schema.create("EnvelopeTemp")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("id", String::class.java)
            .addField("re_type", Int::class.javaPrimitiveType)
            .addField("comment", String::class.java)
            .addField("isInvalid", Int::class.javaPrimitiveType)
            .addField("style", Int::class.javaPrimitiveType)
            .addField("traceId", Long::class.javaPrimitiveType)
            .addField("actionId", String::class.java)
            .addField("accessToken", String::class.java)
            .addField("envelopStatus", Int::class.javaPrimitiveType)
            .addField("sign", String::class.java)

        schema.create("MessageDBTemp")
            .addField("msg_id", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("timestamp", Long::class.java)
            .addField("send_state", Int::class.javaPrimitiveType)
            .addField("send_data", ByteArray::class.java)
            .addField("isRead", Boolean::class.javaPrimitiveType)
            .addField("request_id", String::class.java)
            .addField("from_uid", Long::class.java)
            .addField("from_nickname", String::class.java)
            .addField("from_avatar", String::class.java)
            .addField("from_group_nickname", String::class.java)
            .addField("to_uid", Long::class.java)
            .addField("gid", String::class.java)
            .addField("read", Int::class.javaPrimitiveType)
            .addField("msg_type", Int::class.java)
            .addField("survival_time", Int::class.javaPrimitiveType)
            .addField("endTime", Int::class.javaPrimitiveType)
            .addField("readTime", Int::class.javaPrimitiveType)
            .addField("startTime", Int::class.javaPrimitiveType)
            .addField("serverTime", Int::class.javaPrimitiveType)
            .addField("isLocal", Int::class.javaPrimitiveType)
            .addField("isReplying", Int::class.javaPrimitiveType)
            .addRealmObjectField("envelopeMessage", schema.get("EnvelopeTemp")!!)
    }

    //增加撤回消息新字段
    private fun updateV41(schema: RealmSchema) {
        schema.get("MsgCancel")!!
            .addField("role", Int::class.javaPrimitiveType)
    }

    //增加撤回消息新字段
    private fun updateV42(schema: RealmSchema) {
        schema.get("MsgCancel")!!
            .addField("alterantive_name", String::class.java)
    }

    // 增加手机通讯录表
    private fun updateV43(schema: RealmSchema) {
        schema.create("PhoneBean")
            .addField("phoneremark", String::class.java)
            .addField("phone", String::class.java)
    }

    // 增加注销状态字段
    private fun updateV44(schema: RealmSchema) {
        schema.get("UserInfo")!!
            .addField("deactivateStat", Int::class.javaPrimitiveType)
            .addField("friendDeactivateStat", Int::class.javaPrimitiveType)
        schema.get("UserBean")!!
            .addField("deactivateStat", Int::class.javaPrimitiveType)
            .addField("friendDeactivateStat", Int::class.javaPrimitiveType)
    }

    // 增加红包领取人自选
    private fun updateV45(schema: RealmSchema) {
        schema.get("RedEnvelopeMessage")!!
            .addRealmListField("allowUsers", schema.get("MemberUser")!!)
        schema.get("EnvelopeInfo")!!
            .addRealmListField("allowUsers", schema.get("MemberUser")!!)
    }

    // 小助手新版消息
    private fun updateV46(schema: RealmSchema) {
        schema.get("AssistantMessage")!!
            .addField("version", Int::class.javaPrimitiveType)
            .addField("title", String::class.java)
            .addField("time", Long::class.javaPrimitiveType)
            .addField("content", String::class.java)
            .addField("signature", String::class.java)
            .addField("signature_time", Long::class.javaPrimitiveType)
            .addField("items", String::class.java)

        schema.get("RedEnvelopeMessage")!!
            .addField("canReview", Int::class.javaPrimitiveType)
    }

    // 新的申请需要增加uid字段
    private fun updateV47(schema: RealmSchema) {
        schema.get("Remind")!!
            .addField("uid", Long::class.javaPrimitiveType)
    }

    // 删除主键，用于新的申请红点累加
    private fun updateV48(schema: RealmSchema) {
        schema.get("Remind")!!.removePrimaryKey()
    }

    private fun updateV49(schema: RealmSchema) {
        schema.get("ApplyBean")!!
            .addField("phone", String::class.java)
    }

    private fun updateV50(schema: RealmSchema) {
        schema.get("RedEnvelopeMessage")!!
            .addField("hasPermission", Boolean::class.javaPrimitiveType)
        schema.get("EnvelopeTemp")!!
            .addField("hasPermission", Boolean::class.javaPrimitiveType)
            .addField("canReview", Int::class.javaPrimitiveType)
            .addRealmListField("allowUsers", schema.get("MemberUser")!!)
    }

    //通知消息新增入群备注字段
    private fun updateV51(schema: RealmSchema) {
        schema.get("MsgNotice")!!
            .addField("remark", String::class.java)

        schema.get("Remind")!!
            .addField("gid", String::class.java)
    }

    //通知消息新增入群方式字段
    private fun updateV52(schema: RealmSchema) {
        schema.get("MsgNotice")!!
            .addField("joinGroupType", Int::class.javaPrimitiveType)
    }

    //通知消息新增入群方式字段
    private fun updateV53(schema: RealmSchema) {
        schema.get("MsgNotice")!!
            .addRealmListField("ids", String::class.java)
    }

    //增加能否双向清除
    private fun updateV54(schema: RealmSchema) {
        schema.get("UserBean")!!
            .addField("historyClear", Int::class.javaPrimitiveType)
    }

    //新增互动消息实体类、新增朋友圈用户实体类
    private fun updateV55(schema: RealmSchema) {
        schema.create("FriendUserBean")
            .addField("uid", Long::class.javaPrimitiveType!!, FieldAttribute.PRIMARY_KEY)
            .addField("nickname", String::class.java)
            .addField("avatar", String::class.java)
            .addField("alias", String::class.java)
            .addField("content", String::class.java)
            .addField("stat", Int::class.javaPrimitiveType)
            .addField("followStat", Int::class.javaPrimitiveType)
            .addField("lastTime", Long::class.javaPrimitiveType)
            .addField("imid", String::class.java)
            .addField("bgImage", String::class.java)

        schema.create("InteractMessage")
            .addField("msgId", String::class.java, FieldAttribute.PRIMARY_KEY)
            .addField("momentId", Long::class.javaPrimitiveType)
            .addField("momentUid", Long::class.javaPrimitiveType)
            .addField("interactId", Long::class.javaPrimitiveType)
            .addField("resource", String::class.java)
            .addField("content", String::class.java)
            .addField("interactType", Int::class.javaPrimitiveType)
            .addField("resourceType", Int::class.javaPrimitiveType)
            .addField("hadRead", Boolean::class.javaPrimitiveType)
            .addField("greyColor", Boolean::class.javaPrimitiveType)
            .addField("avatar", String::class.java)
            .addField("nickname", String::class.java)
            .addField("fromUid", Long::class.javaPrimitiveType)
            .addField("timeStamp", Long::class.javaPrimitiveType)
    }

    override fun equals(obj: Any?): Boolean {
        return obj is DaoMigration
    }

    override fun hashCode(): Int {
        return 100
    }
}
