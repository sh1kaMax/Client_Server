package utility

import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket

class ServerManager {

    private var mainServerClientSocket: ServerSocket? = null
    private var mainServerServerSocket: ServerSocket? = null
    private val clientPort = 2000
    private val serverPort = 5000
    private lateinit var clientSocket: Socket
    private lateinit var serverSocket: Socket
    private lateinit var inputClientStream: ObjectInputStream
    private lateinit var outputClientStream: ObjectOutputStream
    private lateinit var inputServerStream: ObjectInputStream
    private lateinit var outputServerStream: ObjectOutputStream
    private lateinit var request: Request
    private lateinit var response: CommandResult


    fun run() {
        openServer()
        while (true) {
            clientSocket = connectToClient()!!
            inputClientStream = ObjectInputStream(clientSocket.getInputStream())
            outputClientStream = ObjectOutputStream(clientSocket.getOutputStream())
            request = inputClientStream.readObject() as Request
            connectToServer()
            outputServerStream.writeObject(request)
            response = inputServerStream.readObject() as CommandResult
            outputClientStream.writeObject(response)
        }
    }

    private fun openServer() {
        try {
            println("Запуск сервера...")
            mainServerClientSocket = ServerSocket(clientPort)
            println("Сервер успешно запущен")
        } catch (e: IOException) {
            println("Произошла ошибка при запуске сервера!")
        }
    }

    private fun connectToClient(): Socket? {
        return try {
            println("Подключение клиента...")
            clientSocket = mainServerClientSocket!!.accept()
            println("Клиент успешно подключен")
            clientSocket
        } catch (e: IOException) {
            null
        } catch (e: NullPointerException) {
            println("Порт $clientPort занят")
            null
        }
    }

    private fun connectToServer() {
        try {
            println("Подключение сервера...")
            serverSocket = Socket("localhost", serverPort)
            outputServerStream = ObjectOutputStream(serverSocket.getOutputStream())
            inputServerStream = ObjectInputStream(serverSocket.getInputStream())
        } catch (e: IOException) {
            println("error: Произошла ошибка при подключении к серверу!")
        }
    }
}