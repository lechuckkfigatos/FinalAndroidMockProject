package com.example.pftandroidmockproject.domain.model.setting

data  class LocalizedText(
    val vi : String,
    val en : String
) {
    fun get(language: AppLanguage): String{
        return when(language){
            AppLanguage.EN -> en
            AppLanguage.VI -> vi
        }
    }
}
