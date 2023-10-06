
# remake from pluggay
# PlugGay

PlugGay là một plugin đơn giản, dễ sử dụng cho phép quản trị viên máy chủ quản lý các plugin từ trong trò chơi hoặc bảng điều khiển mà không cần phải khởi động lại máy chủ.


## Commands
| Command | Description |
| --------------- | ---------------- |
| /pluggay help | Hiển thị thông tin trợ giúp. |
| /pluggay list [-v] | Danh sách plugins. Dùng "-v" bao gồm các phiên bản. |
| /pluggay info [plugin] | Hiển thị thông tin về plugin. |
| /pluggay usage [plugin] | Liệt kê các lệnh mà plugin đã đăng ký. |
| /pluggay lookup [command] | Tìm plugin mà lệnh được đăng ký. |
| /pluggay load [plugin] | Load một plugin. |
| /pluggay reload [plugin&#124;all] | load lại (unload/load) một plugin. |
| /pluggay unload [plugin] | hủy load một plugin. |
| /pluggay check [plugin&#124;all] [-f] | Kiểm tra xem plugin có được cập nhật chưa. |

## Permissions
| Permission Node | Default | Description |
| ------------------------- | ---------- | ---------------- |
| pluggay.admin | OP | Cho phép sử dụng tất cả các lệnh PlugGay. |
| pluggay.update | OP | Cho phép người dùng xem tin nhắn cập nhật. |
| pluggay.help | OP | Cho phép sử dụng lệnh trợ giúp. |
| pluggay.list | OP | Cho phép sử dụng lệnh list. |
| pluggay.info | OP | Cho phép sử dụng lệnh info. |
| pluggay.usage | OP | Cho phép sử dụng lệnh usage. |
| pluggay.lookup | OP | Cho phép sử dụng lệnh lookup. |
| pluggay.load | OP | Cho phép sử dụng lệnh load. |
| pluggay.reload | OP | Cho phép sử dụng lệnh reload. |
| pluggay.reload.all | OP | Cho phép sử dụng lệnh reload all. |
| pluggay.unload | OP | Cho phép sử dụng lệnh unload. |
| pluggay.check | OP | Cho phép sử dụng lệnh check. |
| pluggay.check.all | OP | Cho phép sử dụng lệnh check all. |

## Configuration
| File | URL |
| ----- | ------- |
| config.yml | https://github.com/learnjavalorant/PlugGay/blob/master/src/main/resources/config.yml |

