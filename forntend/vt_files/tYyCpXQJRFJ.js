/*!CK:1157394954!*//*1454296870,*/

if (self.CavalryLogger) { CavalryLogger.start_js(["Y5WeP"]); }

__d('ReactComposerShareNowActionTypes',['keyMirror'],function a(b,c,d,e,f,g,h){if(c.__markCompiled)c.__markCompiled();f.exports=h({SHOW_SHARENOW_MENU:null,SHARE_NOW:null,OPEN_MESSAGE_DIALOG:null,OPEN_SHARE_DIALOG:null,CANCEL_SHARE:null});},null);
__d('ReactComposerShareNowActions',['ReactComposerDispatcher','ReactComposerShareNowActionTypes'],function a(b,c,d,e,f,g,h,i){if(c.__markCompiled)c.__markCompiled();var j={showShareNowMenu:function(){h.dispatch({composerID:'',type:i.SHOW_SHARENOW_MENU});},clickShareNow:function(){h.dispatch({composerID:'',type:i.SHARE_NOW});},clickWritePost:function(){h.dispatch({composerID:'',type:i.OPEN_SHARE_DIALOG});},clickSendMessage:function(){h.dispatch({composerID:'',type:i.OPEN_MESSAGE_DIALOG});},cancelShare:function(k){h.dispatch({composerID:k,type:i.CANCEL_SHARE});}};f.exports=j;},null);