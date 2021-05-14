$(function(){
	$(".follow-btn").click(follow);
});
//$(btn).prev().val() 获取前一个结点的值
//entityType":"3",针对人的
function follow() {
	var btn = this;
	if($(btn).hasClass("btn-info")) {
		$.post(
            CONTEXT_PATH + "/follow",
            {"entityType":"3",
                "entityId":$(btn).prev().val()},
            function (data) {
                data = $.parseJSON(data);
                if (data.code == 0) {
                   window.location.reload();
                } else {
                    alert(data.msg);
                }
            }
		);
		// 关注TA
//		$(btn).text("已关注").removeClass("btn-info").addClass("btn-secondary");
	} else {
        $.post(
            CONTEXT_PATH + "/unfollow",
            {"entityType":"3",
                "entityId":$(btn).prev().val()},
            function (data) {
                data = $.parseJSON(data);
                if (data.code == 0) {
                    window.location.reload();
                } else {
                    alert(data.msg);
                }
            }
        );
		// 取消关注
	//	$(btn).text("关注TA").removeClass("btn-secondary").addClass("btn-info");
	}
}