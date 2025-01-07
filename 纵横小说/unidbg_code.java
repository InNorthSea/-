package com.zongheng.reader;

import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.Emulator;
import com.github.unidbg.Module;
import com.github.unidbg.file.FileResult;
import com.github.unidbg.file.IOResolver;
import com.github.unidbg.linux.android.AndroidEmulatorBuilder;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.*;
import com.github.unidbg.linux.android.dvm.jni.ProxyDvmObject;
import com.github.unidbg.memory.Memory;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class  Zongheng extends AbstractJni implements IOResolver {
    public static AndroidEmulator emulator;
    public static Memory memory;
    public static VM vm;
    public static DalvikModule dm;
    public static Module module;

    public Zongheng(){
        emulator = AndroidEmulatorBuilder.for64Bit().setProcessName("com.zongheng.reader").build();

        memory = emulator.getMemory();

        memory.setLibraryResolver(new AndroidResolver(23));
        vm = emulator.createDalvikVM(new File("E:\\unidbg-0.9.7\\so\\zongheng\\125_bb8cf993ee8a91c08872c984fbd1e0b9.apk"));
        vm.setJni(this);
        vm.setVerbose(true);

        emulator.getSyscallHandler().addIOResolver(this);
        DalvikModule dm = vm.loadLibrary(new File("E:\\unidbg-0.9.7\\so\\zongheng\\libconfusionzh-lib.so"), true);
        dm.callJNI_OnLoad(emulator);

        module = dm.getModule();
    }

    public void getSign(){
        System.out.println("开始调用sign");
        DvmClass confusionClass = vm.resolveClass("com.zongheng.reader.confusion.Confusion");
        Object confusionClassObject = confusionClass.newObject(null);
        Map<String, String> map = new TreeMap<>();
        map.put("apn","wlan");
        map.put("appId","ZHKXS");
        map.put("arch","0");
        map.put("bookId","408586");
        map.put("brand","Google");
        map.put("channelId","zh-vivo");
        map.put("channelType","H5");
        map.put("chapterIds","6905692,6906229,6908056,6909850,6911721");
        map.put("clientVersion","8.1.10.65");
        map.put("did","DUEKkSvydEcAr9aK0MOoUHwrJRYf5gtkjM2fRFVFS2tTdnlkRWNBcjlhSzBNT29VSHdySlJZZjVndGtqTTJmc2h1");
        map.put("installId","1b4d2393bc87a2c97819c4e318518a6d");
        map.put("model","Pixel 2");
        map.put("modelName","walleye");
        map.put("os","android");
        map.put("osVersion","29");
        map.put("pmd5","d41d8cd98f00b204e9800998ecf8427e");
        map.put("preChannelId","zh-vivo");
        map.put("qmid","#7114243412");
        map.put("screenH","1794");
        map.put("screenW","1080");
        map.put("sigKey","566");
        map.put("userId","0");
        DvmObject<?> mapObject = ProxyDvmObject.createObject(vm, map);
        DvmObject<?> context = vm.resolveClass("android/content/Context").newObject(null);
        int i2 = 566;
        DvmObject<?> result = confusionClass.callStaticJniMethodObject(emulator, "getSigValue(Landroid/content/Context;Ljava/util/Map;I)Ljava/lang/String", context, mapObject, i2);
        String dataString = result.getValue().toString();
        System.out.println("Confusion.getSigValue result: " + dataString);
    }

    @Override
    public FileResult resolve(Emulator emulator, String pathname, int oflags) {
        System.out.println("pathName: "+ pathname);
        return null;
    }
    public static void main(String[] args){
        Zongheng zongheng = new Zongheng();
        zongheng.getSign();
    }
    @Override
    public DvmObject<?> callObjectMethodV(BaseVM vm, DvmObject<?> dvmObject, String signature, VaList vaList){
        switch(signature){
            case "java/util/TreeMap->entrySet()Ljava/util/Set;":{
                TreeMap map = (TreeMap) dvmObject.getValue();
                Set entrySet = map.entrySet();
                return ProxyDvmObject.createObject(vm, ((TreeMap<String, String>) dvmObject.getValue()).entrySet());
            }
            case "java/util/TreeMap$EntrySet->iterator()Ljava/util/Iterator;":{
                Set<Map.Entry> entrySet = (Set<Map.Entry>)dvmObject.getValue();
                Iterator<Map.Entry> iterator = entrySet.iterator();
                return ProxyDvmObject.createObject(vm, iterator);
            }
            case "java/util/TreeMap$EntryIterator->next()Ljava/lang/Object;":{
                Iterator<Map.Entry> iterator = (Iterator<Map.Entry>) dvmObject.getValue();
                Map.Entry entry = null;
                if (iterator.hasNext()) {
                    entry = iterator.next();
                    DvmObject<?> keyObject = ProxyDvmObject.createObject(vm, entry.getKey());
                    DvmObject<?> valueObject = ProxyDvmObject.createObject(vm, entry.getValue());
                    System.out.println(keyObject+"=====>"+valueObject);
                }
                return ProxyDvmObject.createObject(vm, entry);
            }
            case "java/util/Map$Entry->getKey()Ljava/lang/Object;":{
                Map.Entry entry = (Map.Entry) dvmObject.getValue();
                return ProxyDvmObject.createObject(vm, entry.getKey());
            }
            case "java/util/Map$Entry->getValue()Ljava/lang/Object;":{
                Map.Entry entry = (Map.Entry) dvmObject.getValue();
                return ProxyDvmObject.createObject(vm, entry.getValue());
            }
        }
        return super.callObjectMethodV(vm, dvmObject, signature,vaList);
    }

    @Override
    public int callIntMethodV(BaseVM vm, DvmObject<?> dvmObject, String signature, VaList vaList){
        switch(signature){
            case "java/util/TreeMap->size()I":{
                TreeMap map = (TreeMap) dvmObject.getValue();
                return map.size();

            }
        }
        return super.callIntMethodV(vm, dvmObject, signature,vaList);
    }

    @Override
    public boolean callBooleanMethodV(BaseVM vm, DvmObject<?> dvmObject, String signature, VaList vaList) {
        switch (signature){
            case "java/util/TreeMap$EntryIterator->hasNext()Z":{
                Iterator<Map.Entry> iterator = (Iterator<Map.Entry>) dvmObject.getValue();
                if(iterator.hasNext()){
                    Map.Entry entry = iterator.next();
                    System.out.println(entry);
                    return true;
                }
            }
            return false;
        }
        return super.callBooleanMethodV(vm, dvmObject, signature, vaList);
    }

}

