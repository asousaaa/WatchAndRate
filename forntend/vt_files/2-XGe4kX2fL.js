/*!CK:2723983494!*//*1450076862,*/

if (self.CavalryLogger) { CavalryLogger.start_js(["O4PU7"]); }

__d("StoryTopicMap",[],function a(b,c,d,e,f,g){if(c.__markCompiled)c.__markCompiled();var h={},i={registerTopics:function(j,k){h[j]=k;},getTopicsForFTID:function(j){return h[j]||[];}};f.exports=i;},null);
__d("XEventRelatedEventsPivotController",["XController"],function a(b,c,d,e,f,g){c.__markCompiled&&c.__markCompiled();f.exports=c("XController").create("\/events\/async\/related_events_pivot\/",{event_id:{type:"Int",required:true},attachment_id:{type:"String",required:true}});},null);
__d("XFeedAdsChainingController",["XController"],function a(b,c,d,e,f,g){c.__markCompiled&&c.__markCompiled();f.exports=c("XController").create("\/feed\/ads_chaining\/",{actor_id:{type:"String",required:true},ft_id:{type:"String"},origin:{type:"String",required:true},ei:{type:"String",required:true},data_ownerid:{type:"String",required:true}});},null);
__d("XPubcontentFeedChainingController",["XController"],function a(b,c,d,e,f,g){c.__markCompiled&&c.__markCompiled();f.exports=c("XController").create("\/pubcontent\/feed_chaining\/",{actor_id:{type:"String",required:true},content_id:{type:"String"},ft_id:{type:"String"},origin:{type:"String",required:true}});},null);
__d("XPubcontentInlineStoryPivotChainingController",["XController"],function a(b,c,d,e,f,g){c.__markCompiled&&c.__markCompiled();f.exports=c("XController").create("\/pubcontent\/inline_story_pivot_chaining\/",{origin:{type:"String",required:true},storyid:{type:"String"},ftid:{type:"String"}});},null);
__d("XPubcontentRelatedShareChainingController",["XController"],function a(b,c,d,e,f,g){c.__markCompiled&&c.__markCompiled();f.exports=c("XController").create("\/pubcontent\/related_share\/",{attachment_div_id:{type:"String",required:true},global_share_id:{type:"Int",required:true},video_div_id:{type:"String"},link_url:{type:"String"},qid:{type:"String"},mf_story_key:{type:"String"},share_id:{type:"String"},is_auto_expand:{type:"Bool",defaultValue:false}});},null);
__d("XPubcontentRelatedVideoChainingController",["XController"],function a(b,c,d,e,f,g){c.__markCompiled&&c.__markCompiled();f.exports=c("XController").create("\/pubcontent\/related_video\/",{attachment_div_id:{type:"String",required:true},fbvideo_id:{type:"Int",required:true},qid:{type:"String"},mf_story_key:{type:"String"}});},null);
__d("XPubcontentTopicChainingController",["XController"],function a(b,c,d,e,f,g){c.__markCompiled&&c.__markCompiled();f.exports=c("XController").create("\/pubcontent\/topic_chaining\/",{pivotal_topic_ids:{type:"IntVector",defaultValue:[]}});},null);
__d("XRelatedGamesChainingController",["XController"],function a(b,c,d,e,f,g){c.__markCompiled&&c.__markCompiled();f.exports=c("XController").create("\/games\/async\/related_games\/",{attachment_div_id:{type:"String",required:true},app_id:{type:"Int",required:true}});},null);
__d("XSearchRelatedPostsPivotController",["XController"],function a(b,c,d,e,f,g){c.__markCompiled&&c.__markCompiled();f.exports=c("XController").create("\/search\/async\/related_posts_pivot\/",{origin:{type:"String",required:true},content_id:{type:"String",required:true}});},null);