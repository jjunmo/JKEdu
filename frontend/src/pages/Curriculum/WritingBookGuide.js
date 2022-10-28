import styled from "styled-components";

import "App.css";

const Main = styled.div`
  box-sizing: border-box;
  display: grid;
  width: 1000px;
  grid-template-columns: repeat(6, 1fr);
  font-size: 20px;
  font-family: SCDream5;

  border-top: 1px solid black;
  border-left: 1px solid black;

  margin: 20px auto;
`;

const Name = styled.div`
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;

  grid-row: 1 / 4;
  border-bottom: 1px solid black;
  border-right: 1px solid black;
`;

const MainBookTitle = styled(Name)`
  grid-row: 1 / 3;
`;
const SubBookTitle = styled.div`
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid black;
  border-right: 1px solid black;
`;

const Seanson = styled(SubBookTitle)``;
const MainBook = styled(SubBookTitle)`
  padding: 10px 0;
`;
const SubBook = styled(SubBookTitle)`
  padding: 10px 0;
`;
const BookImg = styled.img`
  width: 140px;
  height: 180px;
`;

const WritingBookGuide = () => {
  return (
    <Main>
      <Name>
        Writing
        <br />
        Level : A1
      </Name>
      <MainBookTitle>주교재</MainBookTitle>
      <Seanson>1분기</Seanson>
      <Seanson>2분기</Seanson>
      <Seanson>3분기</Seanson>
      <Seanson>4분기</Seanson>
      <MainBook>
        <BookImg src="http://www.alist.co.kr/books_image/04WM0001SB.jpg"></BookImg>
      </MainBook>
      <MainBook>
        <BookImg src="http://www.alist.co.kr/books_image/04WM0002SB.jpg"></BookImg>
      </MainBook>
      <MainBook>
        <BookImg src="http://www.alist.co.kr/books_image/04WM0003SB.jpg"></BookImg>
      </MainBook>
      <MainBook>
        <BookImg src="http://image.yes24.com/momo/TopCate55/MidCate05/5446715.jpg"></BookImg>
      </MainBook>
      <SubBookTitle>부교재</SubBookTitle>
      <SubBook>
        <BookImg></BookImg>
      </SubBook>
      <SubBook></SubBook>
      <SubBook></SubBook>
      <SubBook></SubBook>
    </Main>
  );
};

export default WritingBookGuide;
