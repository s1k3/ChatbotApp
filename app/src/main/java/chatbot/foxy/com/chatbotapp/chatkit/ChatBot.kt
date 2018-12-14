package chatbot.foxy.com.chatbotapp.chatkit

import com.stfalcon.chatkit.commons.models.IUser

class ChatBot : IUser {
    
    override fun getAvatar() = null

    override fun getName() = "Chat Bot"

    override fun getId() = "chat_bot"
}