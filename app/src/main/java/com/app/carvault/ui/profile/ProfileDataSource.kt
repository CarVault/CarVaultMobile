package com.app.carvault.ui.profile

class ProfileDataSource {

    private var profile: Profile = getCurrentUser()

    fun getProfile(): Profile{
        return profile
    }

    // Return Profile with the credentials of the current user
    private fun getCurrentUser(): Profile{
        return Profile(
            id = 1,
            name = "Manuel Framil",
            email = "manuelframil@carvault.com",
            phone = "123 123 123"
        )
    }

    fun updateProfile(newName: String?, newEmail: String?, newPhone: String?){
        if (newName != null)  { profile.name = newName   }
        if (newEmail != null) { profile.email = newEmail }
        if (newPhone != null) { profile.phone = newPhone }
    }

}