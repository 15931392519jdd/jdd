var userObj;

// 用户管理页面上点击删除按钮弹出删除框(userlist.jsp)
function deleteUser(obj) {
	$.ajax({
		type : "GET",
		url : path + "/user/delete",
		data : {
			id : obj.attr("userid")
		},
		dataType : "json",
		success : function(data) {
			console.log(data+";"+typeof(data));
			if (data.delResult == "true") {// 删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			} else if (data.delResult == "false") {// 删除失败
				// alert("对不起，删除用户【"+obj.attr("username")+"】失败");
				changeDLGContent("对不起，删除用户【" + obj.attr("username") + "】失败");
			} else if (data.delResult == "notexist") {
				// alert("对不起，用户【"+obj.attr("username")+"】不存在");
				changeDLGContent("对不起，用户【" + obj.attr("username") + "】不存在");
			}
		},
		error : function(data) {
			console.log(data.delResult);
			// alert("对不起，删除失败");
			changeDLGContent("对不起，删除失败");
		}
	});
}

function openYesOrNoDLG() {
	$('.zhezhao').css('display', 'block');
	$('#removeUse').fadeIn();
}

function cancleBtn() {
	$('.zhezhao').css('display', 'none');
	$('#removeUse').fadeOut();
}
function changeDLGContent(contentStr) {
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

$(function() {
	// 通过jquery的class选择器（数组）
	// 对每个class为viewUser的元素进行动作绑定（click）
	/**
	 * bind、live、delegate on
	 */
	$(".viewUser").on("click", function() {
		// 将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		$.ajax({
			type : "GET",
			url : path + "/user/view",
			data : {
				uid : obj.attr("userid")
			},
			dataType : "json",
			success : function(data) {
				console.log(data);
				$("#v_userCode").val(data.userCode);
				$("#v_userName").val(data.userName);
				if (data.gender == 1) {
					$("#v_gender").val("男");
				} else {
					$("#v_gender").val("女");
				}
				$("#v_birthday").val(data.birthday);
				$("#v_phone").val(data.phone);
				switch (data.userRole) {
				case 1:
					$("#v_userRoleName").val("系统管理员");
					break;
				case 2:
					$("#v_userRoleName").val("经理");
					break;
				case 3:
					$("#v_userRoleName").val("普通员工");
					break;
				}

				$("#v_address").val(data.address);
			},
			error : function(data) {
				console.log(data);
			}
		});
	});

	$(".modifyUser").on("click",function() {
				var obj = $(this);
				window.location.href=path+"/user/usermodify/"+ obj.attr("userid")+"/modify";

			});

	$('#no').click(function() {
		cancleBtn();
	});

	$('#yes').click(function() {
		deleteUser(userObj);
	});

	$(".deleteUser").on("click", function() {
		userObj = $(this);
		changeDLGContent("你确定要删除用户【" + userObj.attr("username") + "】吗？");
		openYesOrNoDLG();
	});
});