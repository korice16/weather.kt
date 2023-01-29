package com.example.weatherapp.viewmodel

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object DataStorageUtil {
    enum class StorageType{
        APP_INNER_DATA, APP_INNER_CACHE, EXTERNAL_DATA, EXTERNAL_CACHE
    }

    val prefCitiesFileName="favorite_cities.txt"
    val prefThresholdFileName="threshold_Weather.txt"

    private fun isExternalStorageWritable():Boolean{
        return Environment.getExternalStorageState()==Environment.MEDIA_MOUNTED
    }

    /*
    creer le fichier
     */
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

    /**
     * sauvegarde n'importe quel fichier en txt
     */
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

    /**
     * sauvegarde la ville dans fichier, dans un certains formats
     */
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
            var txt= addCity(fileTxt,fileContent)
            txt=txt.replace(" ","")
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

    /**
     * Recuper n'importe quel fichier et le renvoie en String
     */
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

    /**
     * Recuper les villes enregistrer en txt et LE retourne en en arrayList<String>
     */
    fun loadCitiesFromFile(contex: Context,storageType: StorageType, fileName: String, vararg childFolders: String): ArrayList<String>{
        var folder:File=selectFolder(contex, storageType)

        for(i in childFolders.indices){
            folder=File(folder, childFolders[i])

            if(!folder.exists()){
                return ArrayList()
            }
        }

        val file=File(folder, fileName)
        if(!file.exists()){
            return ArrayList()
        }

        val fileInputStream=FileInputStream(file)
        val byteArray=fileInputStream.readBytes()
        val fileLoad=String(byteArray, charset("UTF-8"))

        var array_fc: ArrayList<String>
        array_fc = fileLoad.split(",").toTypedArray().toCollection(ArrayList<String>())

        return array_fc
    }

    /**
     * Le type de sauvegarde
     */
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

    /**
     * fonction qui ajoute une Ville
     */
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
        Log.d("dtu",array_fc.toString())
        return array_fc.joinToString()
    }
}