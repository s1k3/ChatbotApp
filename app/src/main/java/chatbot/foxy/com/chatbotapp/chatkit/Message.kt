package chatbot.foxy.com.chatbotapp.chatkit

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import com.stfalcon.chatkit.commons.models.MessageContentType
import java.util.*

class Message(private var user: IUser, val message: String) : IMessage, MessageContentType.Image {

    override fun getId() = user.id

    override fun getCreatedAt() = Date()

    override fun getUser() = user

    override fun getText() = message

    override fun getImageUrl() = user.avatar
}