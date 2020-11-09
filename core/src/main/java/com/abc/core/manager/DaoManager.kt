package com.abc.core.manager

import com.im.db.DaoMigration
import io.realm.RealmConfiguration

/**
 *@author Liszt
 *@date 2020/11/7
 *Description
 */
object DaoManager {
    private var config: RealmConfiguration? = null

    //放在初始化中
    fun initConfig(dbName: String) {
        //---------------重要-------------------------
        //数据库版本,数据库如果有变动,需要修改2个地方
        // 1.dbVer的版本号+1
        // 2.DaoMigration类中migrate()处理升级之后的字段
        //-------------------------------------------
        val dbVer: Long = 55
        config = RealmConfiguration.Builder()
            .name("$dbName.realm")//指定数据库的名称。如不指定默认名为default。
            .schemaVersion(dbVer)
            //.deleteRealmIfMigrationNeeded()//声明版本冲突时自动删除原数据库，开发时候打开
            .migration(DaoMigration())//数据库版本升级处理
            .build()


    }
}