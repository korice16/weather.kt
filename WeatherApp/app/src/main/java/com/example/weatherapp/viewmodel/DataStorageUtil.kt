package com.example.weatherapp.viewmodel

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object DataStorageUtil {

    enum class StorageType{
        APP_INNER_DATA, APP_INNER_CACHE, EXTERNAL_DATA, EXTERNAL_CACHE
    }

    private fun isExternalStorageWritable():Boolean{
        return Environment.getExternalStorageState()==Environment.MEDIA_MOUNTED
    }

    fun createFile(contex: Context, storageType: StorageType, fileName: String, vararg childFolders: String): File{
        var folder:File=selectFolder(contex, storageType)

        //create child folder if you have
        for(i in childFolders.indices){
            folder=File(folder, childFolders[i])

            if(!folder.exists()){
                folder.mkdir()
            }
        }

        //create file
        val file=File(folder, fileName)
        if(!file.exists()){
            file.createNewFile()
        }

        return file
    }

    fun storeTextInfoFile(contex: Context,storageType: StorageType, fileName: String, fileContent: String, vararg childFolders: String): Boolean{
        try {
            var folder:File=selectFolder(contex, storageType)

            //create child folder if you have
            for(i in childFolders.indices){
                folder=File(folder, childFolders[i])

                if(!folder.exists()){
                    folder.mkdir()
                }
            }

            //create file
            val file=File(folder, fileName)
            if(!file.exists()){
                file.createNewFile()
            }

            //write into fila
            val fileOutputStream= FileOutputStream(file)
            val byteArray=fileContent.toByteArray(charset("UTF-8"))
            fileOutputStream.write(byteArray,0,byteArray.size)
            fileOutputStream.close()
            return true
        }
        catch (e: Exception){
            e.printStackTrace()
            return false
        }

    }

    fun saveCityIntoStorage(contex: Context,storageType: StorageType, fileName: String, fileContent: String, vararg childFolders: String): Boolean{
        try {
            var folder:File=selectFolder(contex, storageType)

            //create child folder if you have
            for(i in childFolders.indices){
                folder=File(folder, childFolders[i])

                if(!folder.exists()){
                    folder.mkdir()
                }
            }

            //create file
            var fileTxt=""
            val file=File(folder, fileName)
            if(!file.exists()){
                file.createNewFile()
            }else{//file existe
                val fileInputStream=FileInputStream(file)
                val byteArray=fileInputStream.readBytes()
                fileTxt+= String(byteArray, charset("UTF-8"))
            }

            //fileTxt+=fileContent
            val txt= addCity(fileTxt,fileContent)
            //write into fila
            val fileOutputStream= FileOutputStream(file)
            val byteArray=txt.toByteArray(charset("UTF-8"))
            fileOutputStream.write(byteArray,0,byteArray.size)
            fileOutputStream.close()
            return true
        }
        catch (e: Exception){
            e.printStackTrace()
            return false
        }

    }

    fun loadTextFromFile(contex: Context,storageType: StorageType, fileName: String, vararg childFolders: String): String{
        var folder:File=selectFolder(contex, storageType)

        for(i in childFolders.indices){
            folder=File(folder, childFolders[i])

            if(!folder.exists()){
                return ""
            }
        }

        val file=File(folder, fileName)
        if(!file.exists()){
            return ""
        }

        val fileInputStream=FileInputStream(file)
        val byteArray=fileInputStream.readBytes()
        return String(byteArray, charset("UTF-8"))
    }

    private fun selectFolder(contex: Context, storageType: StorageType):File{
        return when(storageType){
            StorageType.APP_INNER_DATA->{
                contex.filesDir
            }
            StorageType.APP_INNER_CACHE->{
                contex.cacheDir
            }
            StorageType.EXTERNAL_DATA->{
                if(!isExternalStorageWritable()){
                    contex.filesDir
                }
                else{
                    val externlaStorageVolumes:Array<out File> = ContextCompat.getExternalFilesDirs(contex, null)
                    //contex.filesDir
                    if(externlaStorageVolumes.isEmpty()){
                        contex.filesDir
                    }else{
                        externlaStorageVolumes[0]
                    }
                }
            }
            StorageType.EXTERNAL_CACHE->{
                if(!isExternalStorageWritable()){
                    contex.filesDir
                }
                else{
                    contex.externalCacheDir ?: contex.cacheDir
                }
            }
        }

    }

    private fun addCity(fileLoad: String,newCity:String):String{
        /*
        val list= array.joinToString(
            prefix = "[",
            separator = ",",
            postfix="]"
        )
         */
        var array_fc: MutableList<String>

        if(fileLoad.isBlank()){
            array_fc= mutableListOf(newCity)
        }else {
            array_fc = fileLoad.split(",").toTypedArray().toMutableList()
            if(!array_fc.contains(newCity)){
                array_fc.add(newCity)
            }
        }

        return array_fc.joinToString()
    }
}