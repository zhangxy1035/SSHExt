Ext.onReady(function(){
	//指定每页显示的条数
	var itemsPerPage = 15;
	//创建表格数据模型
	Ext.define('Dep', {
	    extend: 'Ext.data.Model',
	    fields: ['id','depName','depTel','schoolName']
	});
	//定义数据源
	var depStore = Ext.create('Ext.data.Store',{
		autoLoad:{start:0,limit:itemsPerPage},
		model:'Dep',
		pageSize:itemsPerPage,
		proxy:{
			type:'ajax',
			url:'dep/dep_getDepInfo.action',
			reader:{
				type:'json',
				totalProperty:'results',
				root:'rows'
			}
		}
	});
	//创建工具栏
	var toolbar = [
	{text:'新增部门',iconCls:'add',handler:showAdd},
	{text:'修改部门',iconCls:'update',handler:showUpdate},
	{text:'删除部门',iconCls:'delete',handler:showDelete}
	];
	
	//创建表格
	var depGrid = Ext.create('Ext.grid.Panel',{
		tbar:toolbar,
		region:'center',
		store:depStore,
		selModel:new Ext.selection.CheckboxModel(),
		columns:[
		{text:'部门ID',width:80,dataIndex:'id',sortabel:true},
		{text:'部门名称',width:80,dataIndex:'depName'},
		{text:'部门电话',width:80,dataIndex:'depTel'},
		{text:'校区',width:80,dataIndex:'schoolName'}
		],
		bbar:[{
			xtype:'pagingtoolbar',
			store: depStore,
			width:600,
			displayInfo:true
		}]
	});
	
	//创建viewport
	Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[depGrid]
	});
	
	//创建表单
	Ext.QuickTips.init();
	//创建校区的数据模型
	Ext.define('School', {
	    extend: 'Ext.data.Model',
	    fields: ['id','schoolName']
	});
	
	var storeBySchool = new Ext.data.Store({
		autoLoad:true,
		model:'School',
		proxy:{
			type:'ajax',
			url:'dep/dep_getSchools.action',
			reader:{
				type:'xml',
				record:'School'
			}
		}
	});
	var depForm = Ext.create('Ext.form.Panel',{
		filedDefault:{
			labelSeparator:':',
			labelWidth:80,
			msgTarget:'side',
			width:300
		},
		bodyPadding:5,
		frame:true,
		items:[{
			xtype:'textfield',
			name:'dep.depName',
			id:'depName',
			allowBlank:false,
			fieldLabel:'部门名称'
		},{
			xtype:'textfield',
			name:'dep.depTel',
			id:'depTel',
			allowBlank:false,
			fieldLabel:'部门电话'
		},{
			xtype:'combo',
			name:'sid',
			id:'combo',
			allowBlank:false,
			blankText:'校区必须填写',
			store:storeBySchool,
			allQuery:'allSchool',
			triggerAction:'all',
			editable:false,
			loadingText:'正在加载校区信息',
			displayField:'schoolName',
			valueField:'id',
			value:'',
			emptyText:'请选择校区',
			queryMode:'remote',
			fieldLabel:'校区'
		},{
			xtype:'hidden',
			name:'dep.id',
			id:'id'
		}],
		buttons:[{text:'提交',handler:submitForm},
		        {text:'关闭',handler:function(){win.hide();}}
		]
	});
	
	//创建窗口
	var win = Ext.create('Ext.window.Window',{
		layout:'fit',
		width:300,
		closeAction:'hide',
		height:280,
		resizeavle:false,
		closeable:true,
		items:[depForm]
	});
	
	function submitForm() {
		if(depForm.isAdd) {
		depForm.form.submit({
			clientValidation:true,
			waitMsg:'正在提交数据请稍后',
			waitTitle:'提示',
			url:'dep/dep_add.action',
			method:'POST',
			success:function(form,action) {
				win.hide();
				Ext.Msg.alert('提示','添加部门成功');
				depStore.load();
			},
			failure:function(form,action) {
				Ext.Msg.alert('提示','添加部门失败');
			}
		});
		} else {
			depForm.form.submit({
				clientValidation:true,
				waitMsg:'正在提交数据请稍后',
				waitTitle:'提示',
				url:'dep/dep_update.action',
				method:'POST',
				success:function(form,action) {
					win.hide();
					Ext.Msg.alert('提示','修改部门成功');
					depStore.load();
				},
				failure:function(form,action) {
					Ext.Msg.alert('提示','修改部门失败');
				}
			});
		}
	}
	
	//显示添加
	function showAdd() {
		depForm.form.reset();
		depForm.isAdd = true;
		win.setTitle('新增部门');
		win.show();
	}
	
	//显示修改
	function showUpdate() {
		//获取对应的id
		var idList = getDepIdList();
		var num = idList.length;
		if(num>1) {
			Ext.Msg.alert('提示','每次只能操作一条数据');
		} else {
			depForm.form.reset();
			win.setTitle('修改部门');
			win.show();
			loadForm(idList[0]);
		}
	}
	
	function getDepIdList() {
		var recs = depGrid.getSelectionModel().getSelection();
		var idList = [];
		if(recs.length==0) {
			Ext.Msg.alert('提示','请选择你需要操作的数据');
			return;
		} else {
			for(var i=0;i<recs.length;i++) {
				var rec = recs[i];
				idList.push(rec.get('id'));
			}
		}
		return idList;
	}
	
	function loadForm(id) {
		depForm.form.load({
			waitMsg:'正在加载数据请稍后',
			waitTitle:'提示',
			url:'dep/dep_getDepById.action',
			params:{id:id},
			failure:function(form,action) {
				Ext.Msg.alert('提示','修改部门失败');
			}
		});
	}
	
	function showDelete() {
		var idList = getDepIdList();
		var num = idList.length;
		if(num==0) {
			Ext.Msg.alert('提示','请选择你需要操作的数据');
			return;
		} else {
			Ext.Msg.confirm('提示','确定删除对应的数据吗？',function(btn) {
				if(btn=='yes') {
					deleteDep(idList);
				}
			});
		}
	}
	
	function deleteDep(idList) {
		var ids = idList.join(',');
		var msgTip = Ext.Msg.show({
			title:'提示',
			width:250,
			msg:'正在删除数据请稍后...'
		});
		Ext.Ajax.request({
			url:'dep/dep_delete.action',
			params:{ids:ids},
			method:'POST',
			success:function(form,action) {
				msgTip.hide();
				Ext.Msg.alert('提示','删除校区成功');
				depStore.load();
			},
			failure:function(form,action) {
				msgTip.hide();
				Ext.Msg.alert('提示','删除校区失败');
			}
		});
		
	}
});