'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
//    retrieve username from user input
    username = document.querySelector('#name').value.trim();

//    when username is true, usernamePage = hidden, chatPage = visible
    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }

//    when user is not true, stop default behaviour
    event.preventDefault();
}


function onConnected() {
    // subscribe to the chatroom
    stompClient.subscribe('/chatroom/public', onMessageReceived);

    // add username to server
    stompClient.send("/app/chat.addUser", {},
        JSON.stringify({
            sender: username,
            type: 'JOIN'
        })
    )

//    when successfully connected (hide: connecting...)
    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}
