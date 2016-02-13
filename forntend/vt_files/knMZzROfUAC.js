/*!CK:2331432835!*//*1439181711,*/

if (self.CavalryLogger) { CavalryLogger.start_js(["YQ4Zs"]); }

__d('getMentionableRect',['Rect','UserAgent'],function a(b,c,d,e,f,g,h,i){if(c.__markCompiled)c.__markCompiled();var j=i.isBrowser('Mobile Safari');function k(n){var o=document.selection.createRange().duplicate();o.moveStart('character',-n);return o.getBoundingClientRect();}function l(n){var o=b.getSelection(),p=o.getRangeAt(0),q=p.cloneRange(),r=q.endContainer,s=q.endOffset,t=null;if(s>=n){q.setStart(r,s-n);t=q.getBoundingClientRect();}return t;}function m(n,o){var p=document.selection?k(n):l(n);if(!p)return null;var q=j?'document':'viewport',r=new h(p.top,o?p.right:p.left,p.bottom,o?p.right:p.left,q);return r.convertTo('document');}f.exports=m;},null);