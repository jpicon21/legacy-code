  function openFancybox() {
            $(".fancybox").attr('rel', 'gallery').fancybox({
                beforeLoad: function() {
                    var el, id = $(this.element).data('title-id');
                    if (id) {
                        el = $('#' + id);
                        if (el.length) {
                            this.title = el.html();
                        }
                    }
                },
                padding: 0
            });
        }
        function GetCookie(name) {
            var arg=name+"=";
            var alen=arg.length;
            var clen=document.cookie.length;
            var i=0;
            while (i<clen) {
                var j=i+alen;
                if (document.cookie.substring(i,j)==arg)
                    return "here";
                i=document.cookie.indexOf(" ",i)+1;
                if (i==0) break;
            }
            return null;
        }
        $(document).ready(function (){

            var visit=GetCookie("AdFirstView");
            if (visit==null){
                openFancybox();
                var expire=new Date();
                expire=new Date(expire.getTime()+10000000000000000000);
                document.cookie="AdFirstView=here; expires="+expire;
            }
            $(".fancybox").eq(0).trigger('click');
        });