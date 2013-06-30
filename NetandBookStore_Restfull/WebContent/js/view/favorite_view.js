var app = app || {};
var ENTER_KEY = 13;

$(function() {
	'use strict';
	
	////////////////////////////////////////////////////////////////
	// APP VIEW!!!
	app.FavoriteView = Backbone.View.extend({

		// Instead of generating a new element, bind to the existing skeleton of
		// the App already present in the HTML.
		el: '#favorite_view',
		
		// 단일 아이템에 대한 템플릿 함수를 캐쉬한다.
	    template: _.template( $('#favorite_template').html() ),
	    
	    events:{
	    	'click #fav_upload':'onClickUpload'
	    },
	    
		initialize: function() {
			
		},

		// Re-rendering the App just means refreshing the statistics -- the rest
		// of the app doesn't change.
		render: function() {
			this.$el.html( this.template() );
		    return this;
		},
		
		
		// Event 처리
		upload_view:null,
		
		onClickUpload: function(){
			// 팝업 처럼 사용하게 하려면 어떻게 하는게 좋을까? 그 부분을 일단 고민해 봅시다.
			if( this.upload_view === null ){
				
				this.upload_view = new app.UploadView();

				this.upload_view.Show( this, function(msg){ 
					this.upload_view = null;
				});
				
			}else{
				//this.upload_view.off("close"); // 이벤트 등록을 먼저 해제 한다.
				this.upload_view.Destory();
				this.upload_view = null;
			}
			console.log("onClick upload");
		}

	});
	
});