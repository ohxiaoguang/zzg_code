import sys
from PySide6 import QtCore, QtWidgets, QtGui
from ui_jisuanqi import Ui_MainWindow

class MyWindow(QtWidgets.QMainWindow):
    def __init__(self):
        super(MyWindow,self).__init__()
        # ui =  ui_test1.Ui_Form()
        self.ui = Ui_MainWindow()
        self.ui.setupUi(self)
        self.bind()

    def bind(self):
        self.ui.btn1.clicked.connect(self.click1)
    
    def click1(self):
        input1 = self.ui.input1.value()
        input2 = self.ui.input2.value()
        print(input1)
        print(input2)
        self.ui.result1.setText(str(input2+input1))

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    widget = MyWindow()
    widget.show()
    sys.exit(app.exec())