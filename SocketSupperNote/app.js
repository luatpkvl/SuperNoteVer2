var express = require("express");
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
server.listen(process.env.PORT || 3000);
var content_mess = [];
io.sockets.on("connection",function(socket){
		console.log("cos nguowi ket noi");
		socket.on("client_send_username",function(data){
			console.log("nguoi dung vua gui du lieu"+data);
			socket.un = data;
			console.log(socket.un);
		});
		socket.on("client_send_message",function(data_mess){
			console.log("nguoi dung "+socket.un+" vua gui tin: "+data_mess);
			io.sockets.emit("server_send_mess",{dulieu: socket.un+" : "+data_mess});
		});

});
