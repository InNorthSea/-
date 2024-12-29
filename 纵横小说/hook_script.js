function hook_getSigValue() {
    Java.perform(
        function () {
            var Confusion = Java.use("com.zongheng.reader.confusion.Confusion");
            Confusion.getSigValue.overload('android.content.Context', 'java.util.Map', 'int').implementation = function (a, b, c) {
                var result = "";
                if (b!= null) {
                    var keyset = b.keySet();
                    var it = keyset.iterator();
                    while (it.hasNext()) {
                        var keystr = it.next().toString();
                        var valuestr = b.get(keystr).toString();
                        result += " " + valuestr;
                    }
                }
                console.log("a========>", a);
                console.log("b========>", result);
                console.log("c========>", c);
                var ret = this.getSigValue(a, b, c);
                return ret;
            }
        }
    )
}
function main() {
    hook_getSigValue();
}
setImmediate(main);
