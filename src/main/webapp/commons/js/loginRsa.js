
function convert(id,str){
    if ('505041'==id)
    {
        return str;
    }
    else
    {
        return "A";
    }
}

function rsaKey(key, value, password){
	rsaKey = new RSAKey();
	rsaKey.setPublic(b64tohex(key), b64tohex(value));
	enPassword = hex2b64(rsaKey.encrypt(password));
	return enPassword
}

