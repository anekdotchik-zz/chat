var chat = new function() {
	var transport = function() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else {
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}();
	var lastMessageId = 0;
	var ajax = function(action, data, callback) {
		transport.onreadystatechange = function(response) {
			if (transport.readyState == 4 && transport.status == 200
					&& callback) {
				callback(transport.responseText);
			} else if (transport.readyState == 4) {
				alert(transport.status);
			}
		};
		transport.open("POST", "async?action=" + action + "&id="
				+ lastMessageId, true);
		transport.send(data);
	};
	var append = function(msg) {
		var messages = JSON.parse(msg);
		for ( var item in messages) {
			var message = messages[item];
			addFifo(message);
			if (lastMessageId < message.id) {
				lastMessageId = message.id;
			}
		}
	};
	var addFifo = function(message) {
		var select = document.getElementById('messages');
		while (select.childNodes.length > 10) {
			select.removeChild(select.childNodes[0]);
		}
		var option = document.createElement("option");
		option.appendChild(document.createTextNode(message.user + ": "
				+ message.message));
		if (message.my) {
			option.setAttribute('class', 'my');
		}
		select.appendChild(option);
	};
	this.send = function(data) {
		ajax("send", data, append);
	};
	this.receive = function() {
		ajax('receive', undefined, append);
	};
	this.init = function() {
		setInterval("chat.receive()", 1000);
	};
};