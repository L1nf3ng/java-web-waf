import Config from './config.js';
import axios from 'axios';


const list = (sucess, fail) => {
    let url = Config.base + Config.api.list;
    axios({
        method: 'POST',
        url: url,
        headers: {
            'Access-Control-Allow-Origin': '*'
        }
    }).then(res => {
        sucess && sucess(res);
    }).catch(res => {
        fail && fail(res);
    })
};


const select = (pageNo, sucess, fail) => {
    let url = Config.base + Config.api.select;
    axios.post(url, {
        pageNo: pageNo,
    }).then(res => {
        sucess && sucess(res);
    }).catch(res => {
        fail && fail(res);
    })
};


const add = (params, sucess, fail) => {
    let url = Config.base + Config.api.add;
    axios.post(url, params, {
        headers: {
            contentType: 'application/json;charset=UTF-8'
        }
    }).then(res => {
        sucess && sucess(res);
    }).catch(res => {
        fail && fail(res);
    })
};


const del = (params, sucess, fail) => {
    let url = Config.base + Config.api.del;
    axios.post(url, params, {
        headers: {
            contentType: 'application/json;charset=UTF-8'
        }
    }).then(res => {
        sucess && sucess(res);
    }).catch(res => {
        fail && fail(res);
    })
};


const modify = (params, sucess, fail) => {
    let url = Config.base + Config.api.modify;
    axios.post(url, params, {
        headers: {
            contentType: 'application/json;charset=UTF-8'
        }
    }).then(res => {
        sucess && sucess(res);
    }).catch(res => {
        fail && fail(res);
    })
};

const Server = {
    list,
    select,
    add,
    del,
    modify
}

export default Server;