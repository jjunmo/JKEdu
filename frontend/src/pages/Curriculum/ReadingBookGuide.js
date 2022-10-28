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

const ReadingBookGuide = () => {
  return (
    <Main>
      <Name>
        Reading
        <br />
        Level : A1
      </Name>
      <MainBookTitle>주교재</MainBookTitle>
      <Seanson>1분기</Seanson>
      <Seanson>2분기</Seanson>
      <Seanson>3분기</Seanson>
      <Seanson>4분기</Seanson>
      <MainBook>
        <BookImg src="https://www.ebricks.co.kr/book/986/PV_R402.png"></BookImg>
      </MainBook>
      <MainBook>
        <BookImg src="https://www.ebricks.co.kr/book/987/PV_R403.png"></BookImg>
      </MainBook>
      <MainBook>
        <BookImg src="https://www.ebricks.co.kr/book/975/PV_FR_1.png"></BookImg>
      </MainBook>
      <MainBook>
        <BookImg src="https://www.ebricks.co.kr/book/976/PV_FR_2.png"></BookImg>
      </MainBook>
      <SubBookTitle>부교재</SubBookTitle>
      <SubBook>
        <BookImg src="https://www.ebricks.co.kr/book/738/PV_SR701.png"></BookImg>
      </SubBook>
      <SubBook></SubBook>
      <SubBook></SubBook>
      <SubBook></SubBook>
    </Main>
  );
};

export default ReadingBookGuide;
