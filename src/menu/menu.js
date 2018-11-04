import React from "react";
import { Menu } from "antd";

class LeftMenu extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      listArr: []
    };
  }
  menuItem = e => {
    this.props.getKey(e.key);
  };
  componentWillMount() {}
  render() {
    return (
      <div style={{ width: 180 }}>
        <Menu
          defaultSelectedKeys={["1"]}
          defaultOpenKeys={["sub1"]}
          mode="inline"
          theme="dark"
          onClick={e => this.menuItem(e)}>
          {this.props.listArr && this.props.listArr.length > 0
            ? this.props.listArr.map((item, index) => {
                return (
                  <Menu.Item key={index + 1}>
                    <span className="item">{item}</span>
                  </Menu.Item>
                );
              })
            : null}
        </Menu>
      </div>
    );
  }
}

export default LeftMenu;
