Ext.onReady(function(){
	//顶部
	var topPanel = Ext.create('Ext.panel.Panel',{
		region:'north',
		border:false,
		height:80,
		contentEl:'top',
		margins:'0 0 0 0'
	});
	
	//中间
	var centerPanel = Ext.create('Ext.tab.Panel',{
		region:'center',
		contentEl:'contentIframe',
		id:'mainContent',
		items:[{title:'首页'}]
	});
	
	//创建模型
	Ext.define('Menu', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'text',  type: 'string'},
	        {name: 'url',  type: 'string'}
	    ]
	});
	
	//创建数据(树的数据)
	var school_conf = {
			text:'学校管理',
			leaf:true,
			url:'school/school_index.action'
	};
	
	var dep_conf = {
			text:'部门管理',
			leaf:true,
			url:'dep/dep_index.action'
	};
	
	
	//创建数据源
	var menuStore = Ext.create('Ext.data.TreeStore',{
		model:'Menu',
		proxy:{
			type:'memory',
			data:[school_conf,dep_conf]
		},
		root:{
			text:'管理',
			leaf:false,
			expanded:true
		}
	});
	
	//创建树菜单
	var menuTree = Ext.create('Ext.tree.Panel',{
		title:'管理',
		border:false,
		rootVisible: false,
		store:menuStore,
		hrefTarget:'mainContent',
		useArrows:false,
		listeners:{
			itemclick:function(view,rec,item,index,e){
				if(rec.get('leaf')) {
					changePage(rec.get('url'),rec.get('text'));
				}
			}
		}
	});
	
	
	
	
	//切换内容
	function changePage(url,title) {
		var index = centerPanel.items.length;
		//tab不超过2个
		if(index==2) {
			//索引从0开始
			centerPanel.remove(1);
		}
		//动态添加tab
		var tabPage = centerPanel.add({
			title:title,
			closable:true
		});
		//设置显示当前的tab
		centerPanel.setActiveTab(tabPage);
		Ext.getDom('contentIframe').src=url;
	}
	
	//
	
	//左边
	var westPanel = Ext.create('Ext.panel.Panel',{
		region:'west',
		layout:'accordion',
		width:200,
	    title:'系统菜单',
	    collapsible:true,
	    margins:'0 5px 0 0',
	    items:[menuTree]
	});
	
	//通过viewport显示出来
	Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[topPanel,centerPanel,westPanel]
	});
	
	
});