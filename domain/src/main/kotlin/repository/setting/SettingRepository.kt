package repository.setting

interface SettingRepository {

    suspend fun setEnableScreenNameViewer(enabled: Boolean)
    suspend fun isEnableScreenNameViewer(): Boolean
}