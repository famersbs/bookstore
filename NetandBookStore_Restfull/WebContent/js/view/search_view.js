var app = app || {};
var ENTER_KEY = 13;

$(function() {
	'use strict';
	
	////////////////////////////////////////////////////////////////
	// APP VIEW!!!
	app.SearchView = Backbone.View.extend({

		// Instead of generating a new element, bind to the existing skeleton of
		// the App already present in the HTML.
		el: '#search_view',
		
		// 항목에 지정된 DOM 이벤트
	    events: {
	      'keyup #keyword_txt': 'onKeyPressKeyword'
	    },
		
		// 단일 아이템에 대한 템플릿 함수를 캐쉬한다.
	    template: _.template( $('#search_template').html() ),
	    
	    
		initialize: function() {

		},

		// Re-rendering the App just means refreshing the statistics -- the rest
		// of the app doesn't change.
		render: function() {
			this.$el.html( this.template() );
			this.input = this.$("#keyword_txt");
		    return this;
		},
		
		//event
		onKeyPressKeyword: function(){
			//console.log("sbs " + this.input.val() );
			app.bookList.search_keyword( this.input.val() );
			
		}

	});
	
});