package chatbot.foxy.com.chatbotapp.chatkit

import com.stfalcon.chatkit.commons.models.IUser

class User : IUser {
    override fun getAvatar() = null

    override fun getName() = "User"

    override fun getId() = "user"
}