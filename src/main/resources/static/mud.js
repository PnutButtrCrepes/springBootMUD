var gameText = document.getElementById("gameText");
var userText = document.getElementById("userText");

var stompClient = null;
var username = null;

// FUNCTIONS

function connect(event)
{
	var socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, onConnected, onError);

	//event.preventDefault();
}


function onConnected()
{
	// Subscribe to the Public Topic
	stompClient.subscribe('/connections/updates', onUpdateReceived);
	stompClient.subscribe('/connections/messages', onMessageReceived);
	stompClient.subscribe('/user/connections/messages', onMessageReceived); // this line is required to send to a specific user!!!

	/*
	// Tell your username to the server
	stompClient.send("/app/chat.addUser", {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
    */
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onUpdateReceived(payload)
{
	
}

function onMessageReceived(payload)
{
	var message = JSON.parse(payload.body);
	gameText.innerText = message.message;
}

function onClick()
{
	userText.focus();
}

function onKeyDown(event)
{
	if (event.key === "Enter")
	{
		console.log("Enter key was pressed!");
		
		// send the command to the console and generate a response
		stompClient.send("/app/player.sendCommand", {}, JSON.stringify({ message: userText.value }));
		userText.value = '';
	}	
	else
		console.log("Enter key was not pressed.");
}

// MAIN PROGRAM

connect();

document.addEventListener("click", onClick);
userText.addEventListener("keydown", onKeyDown);