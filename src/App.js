import "./App.css";
import React, { Component } from "react";
import {
  Table,
  Input,
  InputNumber,
  Popconfirm,
  Form,
  Divider,
  Spin
} from "antd";
import LeftMenu from "./menu/menu.js";
import Server from "./server.js";
import FormInput from "./form/form.js";

const FormItem = Form.Item;
const EditableContext = React.createContext();

const EditableRow = ({ form, index, ...props }) => (
  <EditableContext.Provider value={form}>
    <tr {...props} />
  </EditableContext.Provider>
);

const EditableFormRow = Form.create()(EditableRow);

class EditableCell extends Component {
  getInput = () => {
    if (this.props.inputType === "number") {
      return <InputNumber />;
    }
    return <Input />;
  };

  render() {
    const {
      editing,
      dataIndex,
      title,
      inputType,
      record,
      index,
      ...restProps
    } = this.props;
    return (
      <EditableContext.Consumer>
        {form => {
          const { getFieldDecorator } = form;
          return (
            <td {...restProps}>
              {editing ? (
                <FormItem style={{ margin: 0 }}>
                  {getFieldDecorator(dataIndex, {
                    rules: [
                      {
                        required: true,
                        message: `Please Input ${title}!`
                      }
                    ],
                    initialValue: record[dataIndex]
                  })(this.getInput())}
                </FormItem>
              ) : (
                restProps.children
              )}
            </td>
          );
        }}
      </EditableContext.Consumer>
    );
  }
}

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      editingKey: "",
      listArr: [],
      isEmpty: false,
      isLoading: true
    };
    this.listData = [];
    this.firstInit = 0;
    this.columns = [
      {
        title: "id",
        dataIndex: "key",
        width: "10%",
        editable: false,
        align: "center"
      },
      {
        title: "Regular Expression",
        dataIndex: "reg",
        width: "50%",
        editable: true
      },
      {
        title: "description",
        dataIndex: "description",
        width: "25%",
        editable: true
      },
      {
        title: "operation",
        dataIndex: "operation",
        align: "center",
        render: (text, record) => {
          const editable = this.isEditing(record);
          return (
            <div>
              {editable ? (
                <span>
                  <EditableContext.Consumer>
                    {form => (
                      <a
                        onClick={() => this.save(form, record.key)}
                        style={{ marginRight: 8 }}>
                        Save
                      </a>
                    )}
                  </EditableContext.Consumer>
                  <a onClick={() => this.cancel(record.key)}> Cancel </a>
                </span>
              ) : (
                <span>
                  <a onClick={() => this.edit(record.key)}> Edit </a>
                  <Divider type="vertical" />
                  <Popconfirm
                    title="Sure to delete?"
                    onConfirm={() => this.deleteItem(record.key)}>
                    <a> Delete </a>
                  </Popconfirm>
                </span>
              )}
            </div>
          );
        }
      }
    ];
  }

  isEditing = record => {
    return record.key === this.state.editingKey;
  };

  edit(key) {
    this.setState({ editingKey: key });
  }

  deleteItem = key => {
    // console.log(this.state.data);
    // const data = [...this.state.data];
    // this.setState({ data: data.filter(item => item.key !== key) });
    let params = {
      pageNo: this.pageNo,
      num: 1,
      rules: [{ id: key }]
    };

    Server.del(params, res => {
      this.refreshList(res);
    });
  };

  save(form, key) {
    form.validateFields((error, row) => {
      if (error) {
        return;
      }
      console.log(row);
      let params = {
        pageNo: this.pageNo,
        num: 1,
        rules: [
          {
            id: key,
            content: row.reg
          }
        ]
      };
      Server.modify(params, res => {
        this.refreshList(res);
        this.setState({ editingKey: "" });
      });
      //   const newData = [...this.state.data];
      //   const index = newData.findIndex(item => key === item.key);
      //   if (index > -1) {
      //     const item = newData[index];
      //     newData.splice(index, 1, {
      //       ...item,
      //       ...row
      //     });
      //     this.setState({ data: newData, editingKey: "" });
      //   } else {
      //     newData.push(row);
      //     this.setState({ data: newData, editingKey: "" });
      //   }
    });
  }

  cancel = () => {
    this.setState({ editingKey: "" });
  };

  refreshList = res => {
    if (res && res.data && res.data.rules && res.data.rules.length > 0) {
      this.listData[this.pageNo] = res.data.rules.map(item => {
        return {
          key: item.id,
          reg: item.content,
          description: "filtering letters"
        };
      });
      this.setState({
        data: this.listData[this.pageNo]
      });
    } else {
      this.listData[this.pageNo] = [];
      this.setState({
        data: []
      });
    }
  };

  getListData = () => {
    if (this.listData[this.pageNo] && this.listData[this.pageNo].length > 0) {
      this.setState({
        data: this.listData[this.pageNo]
      });
    } else {
      Server.select(this.pageNo, res => {
        this.firstInit++;
        if (this.firstInit == 2) {
          this.setState({
            isLoading: false
          });
        }
        this.refreshList(res);
      });
    }
  };

  getKey = e => {
    this.pageNo = e;
    this.getListData();
  };

  submit = form => {
    let params = {
      pageNo: this.pageNo,
      num: 2,
      rules: [
        {
          id: 1,
          content: form.Reg
        }
      ]
    };
    Server.add(params, res => {
      this.refreshList(res);
    });
  };
  isNull = length => {
    console.log(length);
  };
  componentWillMount() {
    this.pageNo = 1;
    this.getListData();
    let arr = [];
    Server.list(res => {
      if (res.data && res.data.pageNum > 0 && res.data.nameList) {
        this.firstInit++;
        if (this.firstInit == 2) {
          this.setState({
            isLoading: false
          });
        }
        for (let i in res.data.nameList) {
          arr.push(res.data.nameList[i]);
        }
      }
      if (arr.length == 0) {
        this.setState({
          isEmpty: true,
          isLoading: false
        });
        return;
      }
      this.setState({
        listArr: arr
      });
    });
  }

  renserSpin = () => {
    if (this.state.isLoading) {
      return (
        <div className="loading">
          <Spin />
        </div>
      );
    } else {
      return <div />;
    }
  };

  render() {
    const { isLoading, isEmpty } = this.state;
    const components = {
      body: {
        row: EditableFormRow,
        cell: EditableCell
      }
    };

    const columns = this.columns.map(col => {
      if (!col.editable) {
        return col;
      }
      return {
        ...col,
        onCell: record => ({
          record,
          inputType: col.dataIndex === "age" ? "number" : "text",
          dataIndex: col.dataIndex,
          title: col.title,
          editing: this.isEditing(record)
        })
      };
    });

    return (
      <div className="container0">
        {this.renserSpin()}
        {isEmpty ? (
          <div className="container0">
            <img
              src={require("./assets/empty.jpg")}
              alt="空"
              className="empty"
            />
            <div className="emptyText">空</div>
          </div>
        ) : (
          <div className="container">
            <div className="menu">
              <LeftMenu getKey={this.getKey} listArr={this.state.listArr} />
            </div>
            <div className="tableView">
              <div className="addRow">
                <FormInput sumbit={form => this.submit(form)} />
              </div>
              <Table
                components={components}
                bordered
                dataSource={this.state.data}
                columns={columns}
                rowClassName="editable-row"
              />
            </div>
          </div>
        )}
      </div>
    );
  }
}

export default App;
